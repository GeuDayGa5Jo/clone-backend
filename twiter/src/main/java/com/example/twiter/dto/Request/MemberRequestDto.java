package com.example.twiter.dto.Request;

import com.example.twiter.entity.Authority;
import com.example.twiter.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@Getter
@NoArgsConstructor
public class MemberRequestDto {

    private String memberEmail;
    private String memberName;
    private String memberPassword;
    private String dob;

    private Long memberId;

    private Authority authority;

    public MemberRequestDto(Member member){
        this.memberId = member.getMemberId();
        this.memberEmail = member.getMemberEmail();
        this.memberName = member.getMemberName();
        this.dob = member.getDob();
        this.authority = member.getAuthority();
    }
    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(memberEmail, memberPassword);
    }


}
