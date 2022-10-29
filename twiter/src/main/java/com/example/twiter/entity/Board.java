package com.example.twiter.entity;

import com.example.twiter.dto.BoardDto;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column
    private String boardContent;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column
    private boolean retweet;

    @OneToMany(mappedBy = "board")
    private List<Comment> commentList;


    public Board(BoardDto dto, Member member){
        this.member = member;
        this.boardContent = dto.getBoardContent();
        this.retweet = dto.isRetweet();
    }


    public void update(BoardDto dto) {
        this.boardContent = dto.getBoardContent() != null ? dto.getBoardContent() : this.boardContent;
        this.retweet = dto.isRetweet() != this.retweet ? dto.isRetweet() : this.retweet;
    }
}
