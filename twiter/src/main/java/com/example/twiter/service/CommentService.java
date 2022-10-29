package com.example.twiter.service;

import com.example.twiter.dto.CommentReqDto;
import com.example.twiter.dto.CommentResDto;
import com.example.twiter.dto.CommentUpdateReqDto;
import com.example.twiter.dto.ResponseDto;
import com.example.twiter.entity.Board;
import com.example.twiter.entity.Comment;
import com.example.twiter.entity.Member;
import com.example.twiter.repository.BoardRepository;
import com.example.twiter.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    // 댓글 작성
    @Transactional
    public ResponseDto<?> createComment(Member member, CommentReqDto commentReqDto) {
        Board board = boardRepository.findById(commentReqDto.getBoard_id()).orElseThrow(()->new RuntimeException("게시글이 존재하지 않습니다"));
        Comment comment = new Comment(commentReqDto, member, board);
        commentRepository.save(comment);
        CommentResDto commentResDto = new CommentResDto(comment);
        return ResponseDto.success(commentResDto);
    }

    // 댓글 수정
    @Transactional
    public ResponseDto<?> updateComment(Long commentId, Member member, CommentUpdateReqDto commentUpdateReqDto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()->new RuntimeException("댓글을 찾을 수 없습니다")
        );
        if(!comment.getMember().getMemberName().equals(member.getMemberName())) {
            throw new RuntimeException("본인이 작성한 댓글이 아닙니다");
        }
        comment.update(commentUpdateReqDto);
        commentRepository.save(comment);
        return ResponseDto.success(comment);
    }

    //댓글 삭제
    @Transactional
    public ResponseDto<?> deleteComment(Long commentId, Member member) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new RuntimeException("댓글을 찾을 수 없습니다"));

        if(!comment.getMember().getMemberName().equals(member.getMemberName())) {
            throw new RuntimeException("본인이 작성한 댓글이 아닙니다");
        }
        commentRepository.deleteById(commentId);
        return ResponseDto.success("댓글 삭제 성공");
    }
}
