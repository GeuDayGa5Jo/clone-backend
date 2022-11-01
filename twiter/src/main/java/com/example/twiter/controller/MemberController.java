package com.example.twiter.controller;

import com.example.twiter.dto.MemberDto;
import com.example.twiter.security.MemberDetailsImpl;
import com.example.twiter.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody MemberDto memberDto) {
        return memberService.signup(memberDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberDto memberDto) {
        return memberService.login(memberDto);
    }
    @GetMapping("/mypage")
    public ResponseEntity<?> myPage(@AuthenticationPrincipal MemberDetailsImpl memberDetails){
        return memberService.myPage(memberDetails.getMember());
    }


    @PutMapping("/auth/update")
    public ResponseEntity<?> infoUpdate(@ModelAttribute MemberDto memberDto, @AuthenticationPrincipal MemberDetailsImpl memberDetails) throws IOException {
        return memberService.userUpdate(memberDetails,memberDto);
    }


}
