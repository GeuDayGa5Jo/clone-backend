package com.example.twiter.entity;


import com.example.twiter.dto.CommentDto;
import com.example.twiter.entity.util.Timestamp;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Comment extends Timestamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column
    private String commentContent;


    @Column
    private String memberName;

    @Column
    private Long boardId;





    public Comment(CommentDto commentDto, Member member, Long boardId){
        this.commentContent = commentDto.getCommentContent();
        this.memberName = member.getMemberName();
        this.boardId = boardId;
    }

    public void update(CommentDto commentDto) {
        this.commentContent = commentDto.getModifiedComment() != null ? commentDto.getModifiedComment() : this.commentContent;
    }
}
