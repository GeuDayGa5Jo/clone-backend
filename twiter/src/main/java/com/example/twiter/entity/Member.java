package com.example.twiter.entity;


import com.example.twiter.dto.Request.MemberRequestDto;
import com.example.twiter.entity.util.Timestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;

import lombok.Builder;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    private String profileImgUrl ;

    @Column
    private String headerImgUrl;

    @Column
    @JsonIgnore
    private String memberPassword;

    @Column
    @JsonIgnore
    private String bio;

//    @OneToMany(mappedBy = "following")
//    private List<Follow> following;
//
//    @OneToMany(mappedBy = "followers")
//    private List<Follow> followers;

    @Column
    private UUID randomId;

    @Column
    private String dob;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private Authority authority;

    public Member(String memberEmail, String secret_password, String memberName, String dob, Authority authority) {
        this.memberEmail = memberEmail;
        this.memberPassword = secret_password;
        this.memberName = memberName;
        this.dob = dob;
        this.authority = authority;
    }

//    public void infoUpdate(MemberRequestDto memberRequestDto) {
//        this.memberName = memberRequestDto.getMemberName();
//        this.dob = memberRequestDto.getDob();
//    }
//    public void infoUpdate(MemberRequestDto memberDto, String headerImgUrl) {
//        this.memberName = memberDto.getMemberName();
//        this.headerImgUrl = headerImgUrl;
//        this.bio = memberDto.getBio();
//    }
//
//    public void infoUpdateProfile(MemberRequestDto memberDto, String profileImgUrl) {
//        this.memberName = memberDto.getMemberName();
//        this.profileImgUrl = profileImgUrl;
//        this.bio = memberDto.getBio();
//    }

    public void infoUpdate(String bio, String memberName, String profileUrl, String headerUrl) {

        System.out.println("null accepted in infoUpdate");
        this.memberName = memberName != null ? memberName : this.memberName;
        this.headerImgUrl = headerUrl != null ? headerUrl : this.headerImgUrl;
        this.profileImgUrl = profileUrl != null ? profileUrl : this.profileImgUrl ;
        this.bio = bio != null ? bio : this.bio;
    }

}
