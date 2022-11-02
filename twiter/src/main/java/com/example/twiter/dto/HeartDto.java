package com.example.twiter.dto;


import com.example.twiter.entity.Board;
import com.example.twiter.entity.Heart;
import com.example.twiter.entity.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HeartDto {
    private Long boardId;
    private Long memberId;

    public HeartDto(Heart heart){
        this.boardId = heart.getBoard().getBoardId();
        this.memberId = heart.getMember().getMemberId();
    }

    public HeartDto(Member member, Long BoardId) {
        this.boardId = boardId;
        this.memberId = member.getMemberId();
    }
}
