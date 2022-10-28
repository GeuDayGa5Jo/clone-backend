package com.example.twiter.repository;

import com.example.twiter.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<Board,Long> {
}
