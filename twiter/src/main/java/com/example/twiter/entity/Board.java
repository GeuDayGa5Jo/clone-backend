package com.example.twiter.entity;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column()
    private String boardContent;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

}
