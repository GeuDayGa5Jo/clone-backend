package com.example.twiter.service;

import com.example.twiter.dto.MemberDto;
import com.example.twiter.dto.Request.MemberRequestDto;
import com.example.twiter.dto.Response.MemberResponseDto;
import com.example.twiter.dto.ResponseDto;
import com.example.twiter.dto.TokenDto;
import com.example.twiter.entity.Authority;
import com.example.twiter.entity.Member;
import com.example.twiter.entity.RefreshToken;
import com.example.twiter.repository.MemberRepository;
import com.example.twiter.repository.RefreshTokenRepository;
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

@Service
@RequiredArgsConstructor
public class MemberService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public ResponseEntity<?> signup(MemberRequestDto memberRequestDto) {
        String memberEmail = memberRequestDto.getMemberEmail();
        String memberPassword = memberRequestDto.getMemberPassword();
        String passwordConfirm = memberRequestDto.getPasswordConfirm();
        String memberName = memberRequestDto.getMemberName();
        Date DOB = memberRequestDto.getDOB();

        if (memberRepository.existsByMemberEmail(memberEmail)) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }
        if ( !memberPassword.equals( passwordConfirm )) {
            throw new RuntimeException("비밀번호와 비밀번호확인이 일치하지 않습니다.");
        }

        String secret_password = passwordEncoder.encode( memberPassword );

        Member member = new Member( memberEmail, secret_password , memberName, DOB,  Authority.ROLE_USER );
        memberRepository.save(member);

        return new ResponseEntity<>(new MemberDto(member),HttpStatus.OK);
    }

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


        return new ResponseEntity<>(new MemberDto(member), httpHeaders, HttpStatus.OK);



    }
}