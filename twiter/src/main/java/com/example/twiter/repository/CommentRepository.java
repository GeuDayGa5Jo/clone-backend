package com.example.twiter.repository;

import com.example.twiter.entity.Board;
import com.example.twiter.entity.Comment;
import com.example.twiter.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByBoard(Board board);
    List<Comment> findAllByMember(Member member);
}
