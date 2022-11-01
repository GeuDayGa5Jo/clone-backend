package com.example.twiter.repository;

import com.example.twiter.entity.Board;
import com.example.twiter.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findCommentByBoard(Board board);

    List<Comment> findCommentByBoard_BoardId(Long boardId);
}
