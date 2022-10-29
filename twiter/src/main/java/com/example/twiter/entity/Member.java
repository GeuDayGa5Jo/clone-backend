package com.example.twiter.entity;

import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column
    private String memberEmail;

    @Column
    private String memberName;

    @Column
    private String memberPassword;

    @Column
    private String bio;

    @Column
    private Date DOB;

}
