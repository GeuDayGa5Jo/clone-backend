package com.example.twiter.controller;

import com.example.twiter.dto.Request.CommentRequestDto;
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

    @PostMapping("/{boardId}/create")
    public ResponseEntity<?> createComment(
            @AuthenticationPrincipal MemberDetailsImpl memberDetailsImpl,
            @RequestBody CommentRequestDto commentDto,
            @PathVariable Long boardId) {

        return commentService.createComment(memberDetailsImpl.getMember(), commentDto, boardId);
    }

//    댓글 수정 사용 안함
//    @PutMapping("/{commentId}/update")
//    public ResponseEntity<?> updateComment(
//            @PathVariable Long commentId,
//            @AuthenticationPrincipal MemberDetailsImpl memberDetailsImpl,
//            @RequestBody CommentRequestDto commentDto) {
//
//        return commentService.updateComment(commentId, memberDetailsImpl.getMember(), commentDto);
//
//    }

    @DeleteMapping("/{commentId}/delete")
    public ResponseEntity<?> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal MemberDetailsImpl memberDetailsImpl) {

        return commentService.deleteComment(commentId, memberDetailsImpl.getMember());
    }

    @GetMapping
    public ResponseEntity<?> getComment(@AuthenticationPrincipal MemberDetailsImpl memberDetailsImpl) {

        return commentService.getComment(memberDetailsImpl.getMember());
    }
}
