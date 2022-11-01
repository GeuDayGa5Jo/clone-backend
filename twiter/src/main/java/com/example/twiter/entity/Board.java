package com.example.twiter.entity;
import com.example.twiter.dto.Request.BoardRequestDto;
import com.example.twiter.dto.Response.BoardResponseDto;
import com.example.twiter.entity.util.Timestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;

import lombok.Data;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import java.util.UUID;


@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board extends Timestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column
    private String boardContent;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    @Column
    private boolean retweet;


    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();


    @Column
    private String imageFile;


    public Board(BoardRequestDto dto, Member member){
        this.member = member;
        this.boardContent = dto.getBoardContent();
    }

    public Board(String dto, Member member){
        this.member = member;
        this.boardContent = dto;
    }

    public void update(BoardRequestDto dto) {
        System.out.println("this should be fired");
        System.out.println("dto.getBoardContent() = " + dto.getBoardContent());
        this.boardContent = dto.getBoardContent() != null ? dto.getBoardContent() : this.boardContent;
    }

    public void update(BoardRequestDto dto, String imageFile) {
        this.boardContent = dto.getBoardContent() != null ? dto.getBoardContent() : this.boardContent;
        this.imageFile = imageFile;
    }
}
