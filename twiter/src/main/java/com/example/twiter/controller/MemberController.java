package com.example.twiter.controller;

import com.example.twiter.dto.Request.MemberRequestDto;
import com.example.twiter.dto.ResponseDto;
import com.example.twiter.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody MemberRequestDto memberRequestDto) {
        return memberService.signup(memberRequestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberRequestDto memberRequestDto) {
        return memberService.login(memberRequestDto);
    }



}
