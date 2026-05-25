package com.my.quiz.controller;

import com.my.quiz.dto.MemberDto;
import com.my.quiz.dto.MemberStatus;
import com.my.quiz.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    private final MemberService memberService;
    @GetMapping("/member-list")
    public String changeState(Model model){
        List<MemberDto> memberDto = memberService.findAll();
        model.addAttribute("memberList",memberDto);
        return "admin/member-list";
    }
    @PostMapping("/change-state")
    public String changeState(
            @RequestParam String loginId,
            @RequestParam MemberStatus status){

        memberService.changeState(loginId, status);

        return "redirect:/admin/member-list";
    }
}
