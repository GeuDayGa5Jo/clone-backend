package com.example.twiter.entity;


import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column
    private String commentContent;

    @Column
    private Long boardId;

    @Column
    private String memberName;
}
