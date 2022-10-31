package com.example.twiter.repository;

import com.example.twiter.entity.Board;
import com.example.twiter.entity.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long> {
    List<Board>findBoardsByMember(Member member);

    Slice<Board> findAllByOrderByBoardId(Pageable pageable);

}
