package com.example.twiter.service;

import com.example.twiter.dto.*;
import com.example.twiter.entity.Board;
import com.example.twiter.entity.Comment;
import com.example.twiter.entity.Member;
import com.example.twiter.repository.BoardRepository;
import com.example.twiter.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    // 댓글 작성
    @Transactional
    public ResponseEntity<?> createComment(Member member, CommentDto commentDto, Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(()->new RuntimeException("게시글이 존재하지 않습니다"));
        Comment comment = new Comment(commentDto, member, boardId);
        System.out.println(comment);



        commentRepository.save(comment);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    // 댓글 수정
    @Transactional
    public ResponseEntity<?> updateComment(Long commentId, Member member, CommentDto commentDto) {
        Optional<Comment> optional = commentRepository.findById(commentId);
        Comment comment = optional.orElseThrow(()->new RuntimeException("댓글을 찾을 수 없습니다"));
        if(!comment.getMemberName().equals(member.getMemberName())) {
            throw new RuntimeException("본인이 작성한 댓글이 아닙니다.");
        }
        comment.update(commentDto);
        Long boardId = comment.getBoardId();
        return new ResponseEntity<>(commentRepository.findCommentByBoardId(boardId), HttpStatus.OK);
    }

    //댓글 삭제
    @Transactional
    public ResponseEntity<?> deleteComment(Long commentId, Member member) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new RuntimeException("댓글을 찾을 수 없습니다"));

        if(!comment.getMemberName().equals(member.getMemberName())) {
            CommentResDto response = new CommentResDto("작성자가 다릅니다.", HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        commentRepository.deleteById(commentId);
        CommentResDto response = new CommentResDto("삭제되었습니다.", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //댓글 조회
    public ResponseEntity<?> getcomment(Member member) {
        return new ResponseEntity<>(commentRepository.findAll(), HttpStatus.OK);
    }
}
