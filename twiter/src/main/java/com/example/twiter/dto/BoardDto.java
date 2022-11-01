package com.example.twiter.dto;

import com.example.twiter.entity.Board;
import com.example.twiter.entity.Comment;
import com.example.twiter.entity.Member;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {

    private Long boardId;
    private String boardContent;
    private boolean retweet;

    private String memberName;

    private MultipartFile imageFile;

    List<BoardDto> boardDtos = new LinkedList<>();

    List<CommentDto> comments;


    public BoardDto (Board board, List<CommentDto> commentListDto){
        this.boardId = board.getBoardId();
        this.memberName = board.getMember().getMemberName();
        this.boardContent = board.getBoardContent();
        this.retweet = board.isRetweet();
        this.comments = commentListDto;
    }
    public BoardDto (Board board){
        this.boardId = board.getBoardId();
        this.memberName = board.getMember().getMemberName();
        this.boardContent = board.getBoardContent();
        this.retweet = board.isRetweet();

    }


}
