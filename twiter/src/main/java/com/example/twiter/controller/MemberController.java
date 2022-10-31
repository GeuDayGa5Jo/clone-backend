package com.example.twiter.controller;

import com.example.twiter.dto.MemberDto;
import com.example.twiter.dto.Request.MemberRequestDto;
import com.example.twiter.security.MemberDetailsImpl;
import com.example.twiter.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/update")
    public ResponseEntity<?> infoUpdate(@RequestBody MemberDto memberDto, MemberDetailsImpl memberDetails){
        return memberService.userUpdate(memberDetails,memberDto);
    }


}
