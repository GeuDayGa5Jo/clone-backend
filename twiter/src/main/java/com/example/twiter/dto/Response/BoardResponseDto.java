package com.example.twiter.dto.Response;


import com.example.twiter.dto.Request.CommentRequestDto;
import com.example.twiter.entity.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class BoardResponseDto {
    private Long boardId;
    private String boardContent;
    private boolean retweet;
    private Date createAt;
    private Date updatedAt;
    private String memberName;
    private String memberEmail;

    private String profileImgUrl;
    private String imageUrl;
    private int commentCount;
    List<CommentRequestDto> comments;

    //상세 보기 DTO
    public BoardResponseDto(Board board, List<CommentRequestDto> commentDto) {
        this.boardId = board.getBoardId();
        this.boardContent = board.getBoardContent();
        this.retweet = board.isRetweet();
        this.createAt = board.getCreatedAt();
        this.memberName = board.getMember().getMemberName();
        this.imageUrl = board.getImageFile();
        this.commentCount = board.getCommentList().size();
        this.comments = commentDto;
        this.profileImgUrl = board.getMember().getProfileImgUrl();
    }

    public BoardResponseDto(Board board){
        this.boardId = board.getBoardId();
        this.memberName = board.getMember().getMemberName();
        this.boardContent = board.getBoardContent();
        this.retweet = board.isRetweet();
        this.commentCount = board.getCommentList().size();
        this.imageUrl = board.getImageFile();
        this.createAt = board.getCreatedAt();
        this.updatedAt = board.getUpdatedAt();
        this.profileImgUrl = board.getMember().getProfileImgUrl();
        this.memberEmail = board.getMember().getMemberEmail();
    }
}
