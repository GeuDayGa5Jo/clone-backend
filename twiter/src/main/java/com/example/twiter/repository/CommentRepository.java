package com.example.twiter.repository;

import com.example.twiter.entity.Board;
import com.example.twiter.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
