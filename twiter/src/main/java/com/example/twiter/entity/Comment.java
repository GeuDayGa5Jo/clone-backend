package com.example.twiter.entity;


import com.example.twiter.dto.CommentReqDto;
import com.example.twiter.dto.CommentUpdateReqDto;
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


    @Column
    private String memberName;

    @JsonIgnore
    @OnDelete(action= OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "member_id", nullable = false)
    private Member member;

    public Comment(CommentReqDto commentReqDto, Member member, Board board){
        this.commentContent = commentReqDto.getCommentContent();
        this.memberName = member.getMemberName();
        this.member = member;
        this.board = board;
    }

    public void update(CommentUpdateReqDto commentUpdateReqDto) {
        this.commentContent = commentUpdateReqDto.getCommentContent();
    }
}
