package com.example.twiter.dto;

import com.example.twiter.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class CommentResDto {

    private Long boardId;
    private Date createdAt;
    private LocalDateTime updatedAt;
    private Long commentId;
    private String memberName;
    private String commentContent;

    public CommentResDto(Comment comment) {
        this.boardId = comment.getBoard().getBoardId();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
        this.commentId = comment.getCommentId();
        this.memberName = comment.getMemberName();
        this.commentContent = comment.getCommentContent();

    }
}