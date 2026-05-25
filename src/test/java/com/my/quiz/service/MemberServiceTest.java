package com.my.quiz.service;

import com.my.quiz.dto.MemberDto;
import com.my.quiz.dto.MemberStatus;
import com.my.quiz.dto.RoleType;
import com.my.quiz.entity.Member;
import com.my.quiz.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("회원가입 테스트")
    void signup() {
        // Given
        // 가상의 회원정보 Dto 생성
        MemberDto dto = new MemberDto();
        dto.setLoginId("admin@naver.com");
        dto.setPassword("1111");
        dto.setRole(RoleType.ADMIN);
        dto.setStatus(MemberStatus.APPROVED);
        dto.setAnswerTrue(0);
        dto.setAnswerFalse(0);
        memberService.signup(dto);
        // When
        Member member = memberRepository
                .findById(1L).orElse(null);
        // Then
        // 이름 확인
        assertThat(member.getLoginId()).isEqualTo("admin@naver.com");
    }

    @Test
    @DisplayName("로그인 성공")
    void login_success(){
        // Given
        // 가상의 회원정보 Dto 생성
        MemberDto dto = new MemberDto();
        dto.setLoginId("hong@naver.com");
        dto.setPassword("1111");
        dto.setRole(RoleType.USER);
        dto.setStatus(MemberStatus.PENDING);
        dto.setAnswerTrue(0);
        dto.setAnswerFalse(0);
        memberService.signup(dto);

        // When - 로그인을 했을 때
        // 로그인 용 Dto 생성
        MemberDto resultDto = memberService.login(dto);

        // Then - 성공확인
        assertThat(resultDto).isNotNull();
    }
}