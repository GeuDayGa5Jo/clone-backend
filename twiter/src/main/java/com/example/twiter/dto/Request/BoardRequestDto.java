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

    List<CommentRequestDto> comments;

    public BoardRequestDto(Board board, List<CommentRequestDto> commentDto){
        this.boardContent = board.getBoardContent();
        this.imageUrl = board.getImageFile();
        this.comments = commentDto;
    }

}
