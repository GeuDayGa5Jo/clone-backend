package com.example.twiter.dto;

import com.example.twiter.entity.Board;
import com.example.twiter.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentDto {
    private String commentContent;
    private String modifiedComment;
    private String memberName;
//    private Board board;

//    public CommentDto (Comment comment, Board board){
//        this.commentContent = comment.getCommentContent();
//        this.memberName = comment.getMember().getMemberName();
//        this.board = board;
//    }

    public CommentDto(Comment comment){
        this.commentContent= comment.getCommentContent();
        this.memberName = comment.getMember().getMemberName();
    }

}
