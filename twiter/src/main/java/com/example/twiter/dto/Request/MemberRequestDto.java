package com.example.twiter.dto.Request;

import com.example.twiter.dto.Response.BoardResponseDto;
import com.example.twiter.dto.Response.CommentResponseDto;
import com.example.twiter.entity.Authority;
import com.example.twiter.entity.Board;
import com.example.twiter.entity.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Getter
@NoArgsConstructor
public class MemberRequestDto {

    private String memberEmail;
    private String memberName;
    private String dob;
    private String profileImg;
    private String headerImg;
    private Long memberId;
    private String bio;
    List<CommentRequestDto> comments;

    List<BoardResponseDto> boards;

    private String memberPassword;
    @JsonIgnore
    private MultipartFile profileImgUrl;
    @JsonIgnore
    private Authority authority;
    @JsonIgnore
    private MultipartFile headerImgUrl;

    public MemberRequestDto(Member member) {
        this.memberId = member.getMemberId();
        this.memberEmail = member.getMemberEmail();
        this.memberName = member.getMemberName();
        this.dob = member.getDob();
        this.profileImg = member.getProfileImgUrl();
        this.authority = member.getAuthority();
    }

    public MemberRequestDto(Member member, List<BoardResponseDto> dto, List<CommentRequestDto> commentsDto) {
        this.memberId = member.getMemberId();
        this.memberEmail = member.getMemberEmail();
        this.memberName = member.getMemberName();
        this.dob = member.getDob();
        this.authority = member.getAuthority();
        this.boards = dto;
        this.comments = commentsDto;
        this.headerImg = member.getHeaderImgUrl();
        this.profileImg = member.getProfileImgUrl();
        this.bio = member.getBio();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(memberEmail, memberPassword);
    }
}
