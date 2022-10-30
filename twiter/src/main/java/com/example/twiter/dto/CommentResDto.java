package com.example.twiter.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class CommentResDto {

    private String msg;
    private int statusCode;

    public CommentResDto(String msg, int statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }
}