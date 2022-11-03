package com.example.twiter.dto.Request;


import com.example.twiter.entity.Board;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardRequestDto {
    private String boardContent;
    private MultipartFile imageFile;
    @JsonIgnore
    private String imageUrl;
    private boolean retweet;

    private CommentRequestDto commentRequestDto;

    //refactoring point - use this in the getBoard in boardService

//    public BoardRequestDto(Board board, CommentRequestDto commentDto){
//        this.boardContent = board.getBoardContent();
//        this.imageUrl = board.getImageFile();
//        this.commentRequestDto = commentDto;
//    }

}
