package com.example.twiter.entity;

import com.example.twiter.dto.Request.MemberInfoRequestDto;
import com.example.twiter.entity.util.Timestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

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

    public void infoUpdate(MemberInfoRequestDto memberInfoRequestDto) {
        this.memberName = memberInfoRequestDto.getMemberName();
        this.DOB = memberInfoRequestDto.getDOB();

    }
}
