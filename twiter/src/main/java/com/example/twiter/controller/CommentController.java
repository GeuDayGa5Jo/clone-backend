package com.example.twiter.controller;

import com.example.twiter.dto.CommentReqDto;
import com.example.twiter.dto.CommentUpdateReqDto;
import com.example.twiter.dto.ResponseDto;
import com.example.twiter.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class CommentController {

    private final CommentService commentService;

    //댓글 작성
    @PostMapping("/comments/create")
    public ResponseDto<?> createComment(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestBody @Valid CommentReqDto commentReqDto) {
        return commentService.createComment(memberDetails.getMember(), commentReqDto);
    }

    // 댓글 수정
    @PutMapping("/comments/{commentId}")
    public ResponseDto<?> updateComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestBody CommentUpdateReqDto commentUpdateReqDto) {
        return commentService.updateComment(commentId, memberDetails.getMember(), commentUpdateReqDto);
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public ResponseDto<?> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal MemberDetails memberDetails) {
        return commentService.deleteComment(commentId, memberDetails.getMember());
    }
}
