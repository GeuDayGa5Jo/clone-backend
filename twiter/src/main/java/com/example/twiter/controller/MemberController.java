package com.example.twiter.controller;

import com.example.twiter.dto.Request.MemberInfoRequestDto;
import com.example.twiter.dto.Request.MemberRequestDto;
import com.example.twiter.dto.Request.TokenRequestDto;
import com.example.twiter.dto.TokenDto;
import com.example.twiter.security.MemberDetailsImpl;
import com.example.twiter.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    //회원가입
    @PostMapping("/member/signup")
    public ResponseEntity<?> signup(@RequestBody MemberRequestDto memberRequestDto) {
        return memberService.signup(memberRequestDto);
    }
    //로그인
    @PostMapping("/member/login")
    public ResponseEntity<?> login(@RequestBody MemberRequestDto memberRequestDto) {
        return memberService.login(memberRequestDto);
    }
    @PostMapping("/member/reissue")
    public ResponseEntity<?> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        TokenDto tokenDto = new TokenDto();
        return memberService.reissue(tokenRequestDto);
    }
    @PutMapping("/auth/member/update")
    public ResponseEntity<?> userInfoUpdate(@RequestBody MemberInfoRequestDto memberInfoRequestDto, @AuthenticationPrincipal MemberDetailsImpl memberDetails){
        return memberService.userUpdate(memberDetails, memberInfoRequestDto);
    }

}
