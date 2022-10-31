package com.example.twiter.entity;

import com.example.twiter.dto.BoardDto;
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

public class Board {

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


    public Board(BoardDto dto, Member member){
        this.member = member;
        this.boardContent = dto.getBoardContent();
        this.retweet = dto.isRetweet();
    }

    public void update(BoardDto dto) {
        this.boardContent = dto.getBoardContent() != null ? dto.getBoardContent() : this.boardContent;
        this.retweet = dto.isRetweet() != this.retweet ? dto.isRetweet() : this.retweet;
    }

    public void update(BoardDto dto, String imageFile) {
        this.boardContent = dto.getBoardContent() != null ? dto.getBoardContent() : this.boardContent;
        this.retweet = dto.isRetweet() != this.retweet ? dto.isRetweet() : this.retweet;
        this.imageFile = imageFile;
    }
}
