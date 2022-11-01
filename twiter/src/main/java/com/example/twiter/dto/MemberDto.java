package com.example.twiter.dto;

import com.example.twiter.entity.Authority;
import com.example.twiter.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class MemberDto {

    private Long memberId;
    private String memberEmail;
    private String memberName;
    private String memberPassword;
    private Date DOB;
    private Authority authority;

    private String passwordConfirm;
    private MultipartFile profileImgUrl;

    private MultipartFile headerImgUrl;
    private String bio ;

    public MemberDto(Member member){

        this.memberId = member.getMemberId();
        this.memberEmail = member.getMemberEmail();
        this.memberName = member.getMemberName();
        this.DOB = member.getDOB();
        this.authority = member.getAuthority();

    }
    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(memberEmail, memberPassword);
    }



}
