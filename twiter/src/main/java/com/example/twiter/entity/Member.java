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
    @JsonIgnore
    private String bio;


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

    public void infoUpdate(MemberRequestDto memberRequestDto) {
        this.memberName = memberRequestDto.getMemberName();
        this.dob = memberRequestDto.getDob();
    }
}
