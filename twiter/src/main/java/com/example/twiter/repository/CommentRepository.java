package com.example.twiter.repository;

import com.example.twiter.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

//    List<Comment> findAllByBoard(Board board);
//    List<Comment> findAllByMember(Member member);
//
    void deleteAllByBoard_BoardId(Long boardId);
    List<Comment> findCommentByBoard_BoardId(Long boardId);

    List<Comment> findCommentByBoardId(Long boardId);

}
