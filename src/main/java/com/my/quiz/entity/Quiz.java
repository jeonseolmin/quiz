package com.my.quiz.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "quiz") @Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Quiz extends BaseEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private boolean answer = true;

    @Column(nullable = false)
    private String writer;

}
