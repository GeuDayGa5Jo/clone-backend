package com.example.twiter.dto.Request;

import com.example.twiter.dto.Response.BoardResponseDto;
import com.example.twiter.entity.Authority;
import com.example.twiter.entity.Board;
import com.example.twiter.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Getter
@NoArgsConstructor
public class MemberRequestDto {

    private String memberEmail;
    private String memberName;
    private String memberPassword;
    private String dob;

    private Long memberId;
    private MultipartFile profileImgUrl;

    private MultipartFile headerImgUrl;
    private String bio ;
    private Authority authority;

    List<BoardResponseDto> boards;

    public MemberRequestDto(Member member){
        this.memberId = member.getMemberId();
        this.memberEmail = member.getMemberEmail();
        this.memberName = member.getMemberName();
        this.dob = member.getDob();
        this.authority = member.getAuthority();
    }

    public MemberRequestDto(Member member, List<BoardResponseDto> dto){
        this.memberId = member.getMemberId();
        this.memberEmail = member.getMemberEmail();
        this.memberName = member.getMemberName();
        this.dob = member.getDob();
        this.authority = member.getAuthority();
        this.boards = dto;
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(memberEmail, memberPassword);
    }


}
