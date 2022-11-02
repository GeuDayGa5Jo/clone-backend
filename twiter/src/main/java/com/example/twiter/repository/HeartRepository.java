package com.example.twiter.repository;

import com.example.twiter.entity.Heart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart,Long> {
    Optional<Heart> findHeartByBoard_BoardIdAndAndMember_MemberId(Long boardId, Long memberId);
}
