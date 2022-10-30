package com.example.twiter.controller;

import com.example.twiter.security.MemberDetailsImpl;
import com.example.twiter.service.HeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/")
@RequiredArgsConstructor
public class HeartController {

    private final HeartService heartService;

    @PostMapping("{boardId}/heart")
    public ResponseEntity<?> likeHeart(@PathVariable Long boardId , @AuthenticationPrincipal MemberDetailsImpl memberDetails){
        System.out.println("boardId = " + boardId);
        return heartService.likeHeart(boardId,memberDetails.getMember());
    }
}
