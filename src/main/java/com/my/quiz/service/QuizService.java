package com.my.quiz.service;

import com.my.quiz.dto.QuizDto;
import com.my.quiz.entity.Member;
import com.my.quiz.entity.Quiz;
import com.my.quiz.repository.MemberRepository;
import com.my.quiz.repository.QuizRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService  {
    private final QuizRepository quizRepository;
    private final MemberRepository memberRepository;

    //퀴즈 등록
    public boolean insertQuiz(QuizDto quizDto){
        if(quizDto.getContent().trim().isEmpty()){return false;}
        Quiz quiz = QuizDto.toEntity(quizDto);
        quizRepository.save(quiz);
        return true;
    }

    //퀴즈 리스트 반환
    public List<QuizDto> findAll(){
        return quizRepository.findAll()
                .stream()
                .map(QuizDto::toDto)
                .toList();
    }

    public boolean updateQuiz(QuizDto dto) {
        Quiz quiz = quizRepository.findById(dto.getId()).orElse(null);
        if(quiz == null){return false;}
        quiz.setContent(dto.getContent());
        quiz.setAnswer(dto.isAnswer());
        quiz.setWriter(dto.getWriter());
        quizRepository.save(quiz);
        return true;
    }

    public QuizDto findById(Long id){
        Quiz quiz = quizRepository.findById(id).orElse(null);
        return QuizDto.toDto(quiz);
    }

    public QuizDto findRandomQuiz(){
        return QuizDto.toDto(quizRepository.findRandomQuiz());
    }
    @Transactional
    public boolean checkAnswer(Long id, boolean selectedAnswer, String loginId) {
        Quiz quiz = quizRepository.findById(id).orElseThrow(null);
        Member member = memberRepository.findByLoginId(loginId).orElseThrow(null);
        boolean correct = quiz.isAnswer()==selectedAnswer;
        if (correct) member.setAnswerTrue(member.getAnswerTrue()+1);
        else member.setAnswerFalse(member.getAnswerFalse()+1);
        memberRepository.save(member);
        return correct;
    }

    public void delete(Long id) {
        Quiz quiz =quizRepository.findById(id).orElseThrow();
        quizRepository.delete(quiz);
    }
}
