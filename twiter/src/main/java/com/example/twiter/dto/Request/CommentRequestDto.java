package com.example.twiter.dto.Request;

import com.example.twiter.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentRequestDto {
    private String commentContent;
    private String memberName;

    public CommentRequestDto(Comment comment){
        this.commentContent= comment.getCommentContent();
        this.memberName = comment.getMember().getMemberName();
    }
}
