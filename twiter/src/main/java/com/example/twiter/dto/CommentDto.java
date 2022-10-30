package com.example.twiter.dto;

import com.example.twiter.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentDto {
    private String commentContent;
    private String modifiedComment;
    private String membername;
    private Board board;

}
