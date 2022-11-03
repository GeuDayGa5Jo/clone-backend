package com.example.twiter.entity;



import com.example.twiter.dto.Request.CommentRequestDto;
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

    public Comment(CommentRequestDto commentDto, Member member, Board board){
        this.commentContent = commentDto.getCommentContent();
        this.member = member;
        this.board = board;
    }

//    수정 기능 사용 안함
//    public void update(CommentRequestDto commentDto) {
//        this.commentContent = commentDto.getCommentContent() != null ? commentDto.getCommentContent() : this.commentContent;
//    }
}
