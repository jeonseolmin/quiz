package com.my.quiz.entity;

import com.my.quiz.dto.MemberStatus;
import com.my.quiz.dto.RoleType;
import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "members")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Member extends BaseEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column(unique = true, nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column @Enumerated(EnumType.STRING)
    private RoleType role;

    @Column @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Column
    private Integer answerTrue;

    @Column
    private Integer answerFalse;

}
