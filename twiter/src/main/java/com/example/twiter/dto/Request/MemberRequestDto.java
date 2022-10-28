package com.example.twiter.dto.Request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Date;


@Getter
@NoArgsConstructor
public class MemberRequestDto {

    private String memberEmail;
    private String memberName;
    private String memberPassword;
    private String passwordConfirm;
    private Date DOB;



    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(memberEmail, memberPassword);
    }

}
