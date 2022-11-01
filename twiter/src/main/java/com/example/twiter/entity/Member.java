package com.example.twiter.entity;

import com.example.twiter.dto.MemberDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;

import lombok.Builder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column
    private String memberEmail;

    @Column
    private String memberName;


    @Column
    @JsonIgnore
    private String memberPassword;

    @Column
    private String bio;

    @Column
    private String profileImgUrl ;

    @Column
    private String headerImgUrl;


//    @OneToMany(mappedBy = "member")
//    private List<Board> boardList;

//    @Column
//    @OneToMany(mappedBy = "member")
//    private List<Comment> commentList;

    @Column
    @DateTimeFormat(pattern="dd/MM/yyyy")
    private Date DOB;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private Authority authority;




    public Member(String memberEmail, String secret_password, String memberName, Date dob, Authority authority) {

        this.memberEmail = memberEmail;
        this.memberPassword = secret_password;
        this.memberName = memberName;
        this.DOB = DOB;
        this.authority = authority;

    }

    public void infoUpdate(MemberDto memberDto) {
        this.memberName = memberDto.getMemberName();
        this.bio = memberDto.getBio();

    }

    public void infoUpdate(MemberDto memberDto, String headerImgUrl) {
        this.memberName = memberDto.getMemberName();
        this.headerImgUrl = headerImgUrl;
        this.bio = memberDto.getBio();
    }

    public void infoUpdateProfile(MemberDto memberDto, String profileImgUrl) {
        this.memberName = memberDto.getMemberName();
        this.profileImgUrl = profileImgUrl;
        this.bio = memberDto.getBio();
    }

    public void infoUpdate(MemberDto memberDto, String headerImgUrl, String profileImgUrl) {
        this.memberName = memberDto.getMemberName();
        this.headerImgUrl = headerImgUrl;
        this.profileImgUrl = profileImgUrl;
        this.bio = memberDto.getBio();
    }
}
