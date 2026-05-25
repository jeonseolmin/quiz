package com.my.quiz.dto;

import com.my.quiz.entity.Quiz;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class QuizDto {
    private Long id;
    private String content;
    private boolean answer;
    private String writer;

    public static Quiz toEntity(QuizDto dto){
        Quiz quiz = new Quiz();
        quiz.setId(dto.getId());
        quiz.setContent(dto.getContent());
        quiz.setAnswer(dto.isAnswer());
        quiz.setWriter(dto.getWriter());
        return quiz;
    }

    public static QuizDto toDto(Quiz quiz){
        return new QuizDto(
                quiz.getId(),
                quiz.getContent(),
                quiz.isAnswer(),
                quiz.getWriter()
        );
    }
}
