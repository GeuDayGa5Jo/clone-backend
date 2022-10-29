package com.example.twiter.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class CommentUpdateReqDto {

    @NotBlank(message = "댓글 내용이 필요합니다")
    private String commentContent;

}
