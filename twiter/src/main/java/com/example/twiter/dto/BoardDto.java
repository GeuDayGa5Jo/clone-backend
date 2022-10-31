package com.example.twiter.dto;

import com.example.twiter.entity.Board;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {

    private String boardContent;
    private boolean retweet;

    private MultipartFile imageFile;

    List<BoardDto> boardDtos = new LinkedList<>();

    public BoardDto (Board board){
        this.boardContent = board.getBoardContent();
        this.retweet = board.isRetweet();
    }

    public void addBoard(BoardDto boardDto){
        boardDtos.add(boardDto);
    }

}
