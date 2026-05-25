package com.my.quiz.dto;

import com.my.quiz.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class MemberDto {
    private Long no;
    private String loginId;
    private String password;
    private RoleType role;
    private MemberStatus status;
    private Integer answerTrue;
    private Integer answerFalse;

    public static Member toDto(MemberDto dto){
        Member member = new Member();
        member.setNo(dto.getNo());
        member.setLoginId(dto.getLoginId());
        member.setPassword(dto.getPassword());
        member.setRole(dto.getRole());
        member.setStatus(dto.getStatus());
        member.setAnswerTrue(dto.getAnswerTrue());
        member.setAnswerFalse(dto.getAnswerFalse());
        return member;
    }

    public static MemberDto toEntity(Member member){
        return new MemberDto(
           member.getNo(),
           member.getLoginId(),
           member.getPassword(),
           member.getRole(),
           member.getStatus(),
           member.getAnswerTrue(),
           member.getAnswerFalse()
        );
    }



}
