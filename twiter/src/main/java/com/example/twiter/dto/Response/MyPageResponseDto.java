package com.example.twiter.dto.Response;

import com.example.twiter.dto.Request.BoardRequestDto;
import com.example.twiter.dto.Request.CommentRequestDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyPageResponseDto {
    private BoardRequestDto boardRequestDto;
    private CommentRequestDto commentRequestDto;

    public MyPageResponseDto(BoardRequestDto boardRequestDto, CommentRequestDto commentRequestDto){
        this.boardRequestDto = boardRequestDto;
        this.commentRequestDto = commentRequestDto;
    }
}
