package com.example.twiter.repository;

import com.example.twiter.entity.Board;
import com.example.twiter.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    boolean existsByMemberEmail( String memberEmail );

    Optional<Member> findByMemberEmail(String email);
}
