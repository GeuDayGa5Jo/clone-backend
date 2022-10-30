package com.example.twiter.dto;

import com.example.twiter.entity.Board;
import com.example.twiter.entity.Comment;
import com.example.twiter.entity.Member;
import lombok.*;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {

    private String boardContent;
    private boolean retweet;

    private String memberName;

    List<Comment> comments;


    public BoardDto (Board board, List<Comment> commentsList,Member member){
        this.memberName = member.getMemberName();
        this.boardContent = board.getBoardContent();
        this.retweet = board.isRetweet();
        this.comments = commentsList;
    }


}
