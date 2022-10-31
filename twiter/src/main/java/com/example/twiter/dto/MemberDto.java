package com.example.twiter.dto;

import com.example.twiter.entity.Authority;
import com.example.twiter.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class MemberDto {

    private Long memberId;
    private String memberEmail;
    private String memberName;
    private String memberPassword;
    private Date DOB;
    private Authority authority;

    public MemberDto(Member member){

        this.memberId = member.getMemberId();
        this.memberEmail = member.getMemberEmail();
        this.memberName = member.getMemberName();
        this.DOB = member.getDOB();
        this.authority = member.getAuthority();
    }



}
