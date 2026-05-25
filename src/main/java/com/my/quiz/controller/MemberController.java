package com.my.quiz.controller;

import com.my.quiz.dto.MemberDto;
import com.my.quiz.dto.MemberStatus;
import com.my.quiz.dto.RoleType;
import com.my.quiz.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor @Slf4j
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/join")
    public String signUpForm(Model model) {
        model.addAttribute("dto", new MemberDto());
        return "member/join";
    }

    @PostMapping("/join")
    public String signup(@ModelAttribute("dto") MemberDto dto) {
        dto.setRole(RoleType.USER);
        dto.setStatus(MemberStatus.PENDING);
        memberService.signup(dto);
        return "redirect:/member/login";
    }

    @GetMapping("/login")
    public String loginFormView() {
        return "member/login";
    }


    @PostMapping("/login")
    public String login(MemberDto dto, HttpSession session) {
        // HttpSession session : 모델과 똑 같다.
        MemberDto loginedDto = memberService.login(dto);
        if (loginedDto == null) {
            // 로그인 실패 시
            log.info("로그인 실패");
            return "redirect:/member/login";

        } else // 로그인 성공 시
        {
            log.info("로그인 성공");
            // 전체 보내도 됨
            session.setAttribute("loginedDto", loginedDto);
            // 1. 세션에 ID 저장
            session.setAttribute("loginId", loginedDto.getLoginId());
            // 2. 세션에 권한도 저장
            session.setAttribute("role", loginedDto.getRole());
            session.setAttribute("state",loginedDto.getStatus());
            // 3. 세션 유지 시간은?
            session.setMaxInactiveInterval(60*30);  // 60초 * 30 = 30분
            return loginedDto.getRole() == RoleType.USER
                    ? "redirect:/member/my-page"
                    : "redirect:/quiz";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // 세션을 전체 삭제
        session.invalidate();
        // main으로 이동
        return "redirect:/member/login";
    }
    @GetMapping("/my-page")
    public String myPage(HttpSession session, Model model) {
        String loginId = (String) session.getAttribute("loginId");
        MemberDto dto = memberService.findByLoginId(loginId);
        model.addAttribute("dto", dto);
        session.setAttribute("loginedDto", dto);
        return "member/my-page";
    }
    @PostMapping("/password")
    public String updatePassword(HttpSession session,
                                 @RequestParam String password){
        MemberDto dto =  (MemberDto) session.getAttribute("loginedDto");
        dto.setPassword(password);
        memberService.updatePassword(dto);
        log.info("비밀번호 수정 완료");
        return  "redirect:/member/login";
    }


    // 여기까지가 회원 가입 / 회원 처리 / 로그인까지 완료 /
}
