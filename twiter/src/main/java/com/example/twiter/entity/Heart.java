package com.example.twiter.entity;


import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Heart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long heartId;


    @ManyToOne
    private Board board;

    
    @ManyToOne
    private Member member;

    @ManyToOne
    private Comment comment;

}
