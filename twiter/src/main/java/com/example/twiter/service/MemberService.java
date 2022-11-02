package com.example.twiter.service;

import com.example.twiter.dto.Request.CommentRequestDto;
import com.example.twiter.dto.Request.MemberRequestDto;
import com.example.twiter.dto.Request.TokenRequestDto;
import com.example.twiter.dto.Response.BoardResponseDto;
import com.example.twiter.dto.TokenDto;
import com.example.twiter.entity.*;
import com.example.twiter.entity.util.S3Uploader;
import com.example.twiter.exceptionHandler.RestApiExceptionHandler;
import com.example.twiter.repository.BoardRepository;
import com.example.twiter.repository.CommentRepository;
import com.example.twiter.repository.MemberRepository;
import com.example.twiter.repository.RefreshTokenRepository;
import com.example.twiter.security.MemberDetailsImpl;
import com.example.twiter.security.TokenProvider;
import com.example.twiter.security.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



@Service
@RequiredArgsConstructor
public class MemberService {

    //URL 각자 수정
    private final static String PROFILE_URL = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ15YOWd4YH-IdpRVNIqn3T8qeyurc5aEcU8Gsfk5U&s";

    private final static String HEADER_URL = "https://velog.velcdn.com/images/khi95/post/82d69b83-ddfc-41c3-a90c-2bcea0925798/image.png";
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;

    private final RestApiExceptionHandler exceptionHandler;


    private final BoardRepository boardRepository;

    private final S3Uploader s3Uploader;

    @Transactional
    public ResponseEntity<?> signup(MemberRequestDto memberRequestDto){


        if(memberRepository.existsByMemberEmail(memberRequestDto.getMemberEmail())){
            return new ResponseEntity<>("중복된 아이디입니다.",HttpStatus.BAD_REQUEST);
        }

//        if ( !memberPassword.equals( passwordConfirm )) {
//            throw new RuntimeException("비밀번호와 비밀번호확인이 일치하지 않습니다.");
//        }

//        if(!memberRequestDto.getMemberPassword().equals(memberRequestDto.getPasswordConfirm())){
//            return new ResponseEntity<>("비밀번호와 비밀번호확인이 일치하지 않습니다.",HttpStatus.BAD_REQUEST);
//        }


        Member member = Member.builder()
                .memberEmail(memberRequestDto.getMemberEmail())
                .memberPassword(passwordEncoder.encode(memberRequestDto.getMemberPassword()))
                .profileImgUrl(PROFILE_URL)
                .headerImgUrl(HEADER_URL)
                .memberName(memberRequestDto.getMemberName())
                .dob(memberRequestDto.getDob())
                .bio(memberRequestDto.getBio())
                .authority(Authority.ROLE_USER)
                .build();

        memberRepository.save(member);

        return new ResponseEntity<>("회원가입에 성공하셨습니다",HttpStatus.OK);
    }

    @Transactional
    //로그인
    public ResponseEntity<?> login(MemberRequestDto memberRequestDto) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = memberRequestDto.toAuthentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        Member member = (Member) memberRepository.findByMemberEmail( memberRequestDto.getMemberEmail() ).orElse( null );

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER , JwtFilter.BEARER_PREFIX + tokenDto.getAccessToken());
        httpHeaders.add("Refresh-Token" , tokenDto.getRefreshToken());
        // 5. 토큰 발급
//        return new ResponseEntity<>( ResponseDto.success(new MemberResponseDto(member) ), httpHeaders, HttpStatus.OK) ;

        assert member != null;
        return new ResponseEntity<>(new MemberRequestDto(member), httpHeaders, HttpStatus.OK);

    }

    @Transactional
    public ResponseEntity<?> reissue(TokenRequestDto tokenRequestDto) {
        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return new ResponseEntity<>(tokenDto, HttpStatus.OK);
    }
    @Transactional
    public ResponseEntity<?> editProfile(MemberDetailsImpl memberDetails, String bio, String memberName, MultipartFile profileImgUrl, MultipartFile headerImgUrl) throws IOException {

        Member member = memberDetails.getMember();
        if(headerImgUrl == null && profileImgUrl == null){
            member.infoUpdate(bio, memberName, null, null);
            memberRepository.save(member);
            return new ResponseEntity<>("수정이 완료되었습니다", HttpStatus.OK);
        }
        if(headerImgUrl == null){
            String profileUrl = s3Uploader.upload(profileImgUrl,"profile");
            member.infoUpdate(bio, memberName, profileUrl, null);
            memberRepository.save(member);
            return new ResponseEntity<>("수정이 완료되었습니다", HttpStatus.OK);
        }
        String headerUrl = s3Uploader.upload(headerImgUrl,"header");
        if(profileImgUrl == null){
            member.infoUpdate(bio, memberName, null, headerUrl);
        }
        else{
            String profileUrl = s3Uploader.upload(profileImgUrl,"profile");
            member.infoUpdate(bio, memberName, profileUrl, headerUrl);
            memberRepository.save(member);
        }

        return new ResponseEntity<>("수정이 완료되었습니다", HttpStatus.OK);

    }

        // 프로필 사진이 없을때
//        else if (memberRequestDto.getProfileImgUrl() == null) {
//
//            //헤더사진이 기본사진일때
//            if (member.getHeaderImgUrl().equals(HEADER_URL)) {
//
//                member.infoUpdate(memberRequestDto, s3Uploader.upload(memberRequestDto.getHeaderImgUrl(), "header"));
//
//            } else {
//
//                int sliceNum = member.getHeaderImgUrl().lastIndexOf("/", member.getHeaderImgUrl().lastIndexOf("/") - 1);
//                s3Uploader.deleteFile(member.getHeaderImgUrl().substring(sliceNum + 1));
//                member.infoUpdate(memberRequestDto, s3Uploader.upload(memberRequestDto.getHeaderImgUrl(), "header"));
//
//            }
//
//            memberRepository.save(member);
//
//            return new ResponseEntity<>(member, HttpStatus.OK);
//        }
        // 헤더 사진이 없을 때(메소드가 같은 이름 , 같은 갯수의 변수명이라 새로 만듬)
//        else if (memberRequestDto.getHeaderImgUrl() == null) {
//            // 프로필 사진이 기본 사진일 떄
//            if (member.getProfileImgUrl().equals(PROFILE_URL)) {
//
//                member.infoUpdate(memberRequestDto, s3Uploader.upload(memberRequestDto.getProfileImgUrl(), "profile"));
//
//            } else {
//
//                int sliceNum = member.getProfileImgUrl().lastIndexOf("/", member.getProfileImgUrl().lastIndexOf("/") - 1);
//                s3Uploader.deleteFile(member.getProfileImgUrl().substring(sliceNum + 1));
//                member.infoUpdate(memberRequestDto, s3Uploader.upload(memberRequestDto.getProfileImgUrl(), "profile"));
//
//            }
//            memberRepository.save(member);
//
//            return new ResponseEntity<>(member, HttpStatus.OK);
//        }
//// 둘다 사진 있을 때
//        else {
//// 둘다 기본사진일 경우
//            if (member.getProfileImgUrl().equals(PROFILE_URL) && member.getHeaderImgUrl().equals(HEADER_URL)) {
//                member.infoUpdate(memberRequestDto, s3Uploader.upload(memberRequestDto.getHeaderImgUrl(), "header"), s3Uploader.upload(memberRequestDto.getProfileImgUrl(), "profile"));
//                memberRepository.save(member);
//            } else {
//
//                int sliceNum = member.getProfileImgUrl().lastIndexOf("/", member.getProfileImgUrl().lastIndexOf("/") - 1);
//                s3Uploader.deleteFile(member.getHeaderImgUrl().substring(sliceNum + 1));
//                s3Uploader.deleteFile(member.getProfileImgUrl().substring(sliceNum + 1));
//                member.infoUpdate(memberRequestDto, s3Uploader.upload(memberRequestDto.getHeaderImgUrl(), "header"), s3Uploader.upload(memberRequestDto.getProfileImgUrl(), "profile"));
//                memberRepository.save(member);
//
//            }
//            return new ResponseEntity<>(member, HttpStatus.OK);
//        }
//    }

    public ResponseEntity<?> myPage (Member member) {

        List<Board> myBoards = boardRepository.findBoardByMember_MemberId(member.getMemberId());
        List<BoardResponseDto> dtoBoard = new ArrayList<>();
        List<Comment> commentList = commentRepository.findCommentByMember(member);
        List<CommentRequestDto> commentDto = new ArrayList<>();

        for (Comment comment : commentList) {
            commentDto.add(new CommentRequestDto(comment));
        }

        for (Board myBoard : myBoards) {
            dtoBoard.add(new BoardResponseDto(myBoard));
        }


        MemberRequestDto dto = new MemberRequestDto(member,dtoBoard, commentDto);


        return new ResponseEntity<>(dto,HttpStatus.OK);

    }

}
