package com.example.twiter.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Heart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long heartId;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    private Comment comment;

    public Heart(Member member , Board board) {
        this.board = board;
        this.member = member;
    }

    public Heart( Member member, Comment comment) {
        this.member = member;
        this.comment = comment;
    }
}
