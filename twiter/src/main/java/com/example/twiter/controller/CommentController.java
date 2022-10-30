package com.example.twiter.controller;


import com.example.twiter.dto.CommentDto;

import com.example.twiter.security.MemberDetailsImpl;
import com.example.twiter.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/comments")
public class CommentController {

    private final CommentService commentService;

    //댓글 작성

    @PostMapping("/{boardId}/create")
    public ResponseEntity<?> createComment(
            @AuthenticationPrincipal MemberDetailsImpl memberDetailsImpl,
            @RequestBody CommentDto commentDto,
            @PathVariable Long boardId) {
        return commentService.createComment(memberDetailsImpl.getMember(), commentDto, boardId);

    }

    // 댓글 수정
    @PutMapping("/{commentId}/update")
    public ResponseEntity<?> updateComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal MemberDetailsImpl memberDetailsImpl,
            @RequestBody CommentDto commentDto) {
        return commentService.updateComment(commentId, memberDetailsImpl.getMember(), commentDto);

    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}/delete")
    public ResponseEntity<?> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal MemberDetailsImpl memberDetailsImpl) {
        return commentService.deleteComment(commentId, memberDetailsImpl.getMember());
    }

    //댓글 조회
    @GetMapping
    public ResponseEntity<?> getComment(@AuthenticationPrincipal MemberDetailsImpl memberDetailsImpl){
        return commentService.getcomment(memberDetailsImpl.getMember());
    }
}
