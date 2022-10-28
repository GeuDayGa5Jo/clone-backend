package com.example.twiter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
@AllArgsConstructor
public class UserRequestDto {

    private String memberEmail;
    private String memberPassoword;
    private String memberName;
    private String DOB;

    private String passwordConfirm;


    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(memberEmail, password);
    }

}
