package com.example.twiter.entity;


import com.example.twiter.dto.CommentDto;
import com.example.twiter.entity.util.Timestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
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


    @ManyToOne

    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;


    public Comment(CommentDto commentDto, Member member, Board board){
        this.commentContent = commentDto.getCommentContent();
        this.member = member;
        this.board = board;
    }

    public void update(CommentDto commentDto) {
        this.commentContent = commentDto.getModifiedComment() != null ? commentDto.getModifiedComment() : this.commentContent;
    }
}
