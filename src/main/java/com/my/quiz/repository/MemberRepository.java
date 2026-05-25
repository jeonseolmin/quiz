package com.my.quiz.repository;

import com.my.quiz.dto.RoleType;
import com.my.quiz.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByLoginId(String loginId);
    List<Member> findByRoleNot(RoleType role);
}
