package com.my.quiz.controller;

import com.my.quiz.dto.MemberStatus;
import com.my.quiz.dto.QuizDto;
import com.my.quiz.dto.MemberDto;
import com.my.quiz.service.QuizService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller @RequiredArgsConstructor
@RequestMapping("/quiz") @Slf4j
public class QuizController {
    private final QuizService quizService;
    @GetMapping({"","/"})
    public String home(Model model){
        model.addAttribute("quizList",quizService.findAll());
        return "/quiz/list";
    }
    @PostMapping("/insert")
    public String insertQuiz(@RequestParam String content,
                             @RequestParam boolean answer,
                             @RequestParam String writer) {
        QuizDto quizDto = new QuizDto();
        quizDto.setContent(content);
        quizDto.setAnswer(answer);
        quizDto.setWriter(writer);
        quizService.insertQuiz(quizDto);

        return "redirect:/quiz";
    }

    @GetMapping("/play")
    public String play(HttpSession session,
                       RedirectAttributes redirectAttributes,
                       Model model){

        MemberDto member =
                (MemberDto) session.getAttribute("loginedDto");

        // 승인 대기 상태
        if(member.getStatus() == MemberStatus.PENDING){

            redirectAttributes.addFlashAttribute(
                    "message",
                    "관리자 승인 후 플레이 가능합니다."
            );

            return "redirect:/member/my-page";
        }

        // 정상 유저
        model.addAttribute(
                "randomQuiz",
                quizService.findRandomQuiz()
        );

        return "quiz/play";
    }

    @PostMapping("/check")
    public String answerCheck(@RequestParam("quizId") Long quizId,
                              @RequestParam("selectedAnswer") boolean selectedAnswer,
                              HttpSession session,
                              Model model){
        MemberDto memberDto = (MemberDto) session.getAttribute("loginedDto");
        boolean checkAnswer = quizService.checkAnswer(quizId,selectedAnswer,memberDto.getLoginId());
        String result = checkAnswer ? "정답입니다!" : "틀렸습니다!";
        model.addAttribute("result",result);
        return "quiz/result";
    }

    @GetMapping("/{id}")
     public String updateQuizForm(@PathVariable Long id,
                                  Model model){
        QuizDto dto = quizService.findById(id);
        model.addAttribute("dto",dto);
        return "quiz/update";
    }

    @PostMapping("/update")
    public String updateQuiz(@ModelAttribute("dto") QuizDto dto){
        quizService.updateQuiz(dto);
        return "redirect:/quiz";
    }

    @PostMapping("/delete")
    public String deleteQuiz(@RequestParam("id") Long id){
        quizService.delete(id);
        return "redirect:/quiz";
    }
}
