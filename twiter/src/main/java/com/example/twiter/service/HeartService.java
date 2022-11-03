package com.example.twiter.service;

import com.example.twiter.dto.HeartDto;
import com.example.twiter.entity.Board;
import com.example.twiter.entity.Heart;
import com.example.twiter.entity.Member;
import com.example.twiter.repository.BoardRepository;
import com.example.twiter.repository.HeartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
@RequiredArgsConstructor
public class HeartService {

    private final HeartRepository heartRepository;
    private final BoardRepository boardRepository;

    public ResponseEntity<?> likeHeart(Long boardId, Member member) {

        Heart heart = heartRepository.findHeartByBoard_BoardIdAndMember_MemberId(boardId,member.getMemberId()).orElse(null);
        Board board = boardRepository.findById(boardId).orElseThrow(IllegalAccessError::new);

        if(Objects.nonNull(heart)){
            heartRepository.delete(heart);
        }
        if(Objects.isNull(heart)){
            Heart heart1 = new Heart(member,board);
            heartRepository.save(heart1);
        }

        return new ResponseEntity<>(new HeartDto(heart),HttpStatus.OK);

    }
}
