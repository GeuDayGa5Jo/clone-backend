package com.example.twiter.service;

import com.example.twiter.dto.MemberDto;
import com.example.twiter.dto.Request.MemberRequestDto;
import com.example.twiter.dto.Request.TokenRequestDto;
import com.example.twiter.dto.TokenDto;
import com.example.twiter.entity.Authority;
import com.example.twiter.entity.Board;
import com.example.twiter.entity.Member;
import com.example.twiter.entity.RefreshToken;
import com.example.twiter.repository.BoardRepository;
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


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MemberService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;


    private final BoardRepository boardRepository;


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
                .memberName(memberRequestDto.getMemberName())
                .DOB(memberRequestDto.getDOB())
                .authority(Authority.ROLE_USER)
                .build();

        memberRepository.save(member);

        return new ResponseEntity<>(new MemberDto(member),HttpStatus.OK);
    }

    @Transactional
    //로그인
    public ResponseEntity<?> login(MemberRequestDto memberRequestDto) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = memberRequestDto.toAuthentication();
        System.out.println("authenticationToken = " + authenticationToken);

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        Member member = (Member) memberRepository.findByMemberEmail( memberRequestDto.getMemberEmail() ).orElse( null );
        System.out.println("member = " + member);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);
        System.out.println("refreshToken = " + refreshToken);

        HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER , JwtFilter.BEARER_PREFIX + tokenDto.getAccessToken());
        httpHeaders.add("Refresh-Token" , tokenDto.getRefreshToken());
        // 5. 토큰 발급
//        return new ResponseEntity<>( ResponseDto.success(new MemberResponseDto(member) ), httpHeaders, HttpStatus.OK) ;

        return new ResponseEntity<>(new MemberDto(member), httpHeaders, HttpStatus.OK);

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
    public ResponseEntity<?> userUpdate(MemberDetailsImpl memberDetails, MemberDto memberDto) {

        Member member = memberDetails.getMember();
        member.infoUpdate(memberDto);
        memberRepository.save(member);

        return new ResponseEntity<>("수정 되었습니다.", HttpStatus.OK);
    }

    public ResponseEntity<?> myPage(Member member) {

        List<Board> myBoards = boardRepository.findBoardsByMember(member);

        return new ResponseEntity<>(myBoards,HttpStatus.OK);


    }
}
