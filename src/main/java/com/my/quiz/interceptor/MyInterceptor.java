package com.my.quiz.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
@Component
public class MyInterceptor implements HandlerInterceptor {
    // 특정 상황이 됐을 때 컨트롤러를 가로채서
    // 특정 동작을 수행하는 클래스
    // 조건 : 임의의 컨트롤러 시작전에 세션정보를 확인한 후
    // 세션정보가 있으면 해당 컨트롤러 진행
    // 세션정보가 없으면 login 화면으로 이동하도록 강제.

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {
        // 컨트롤러로 전달되는 세션값 받는 것.
        HttpSession session = request.getSession();
        Object loginEmail = session.getAttribute("loginEmail");
        if (ObjectUtils.isEmpty(loginEmail)) {
            // 로그인하지 않은경우
            response.sendRedirect("/member/login");
            return false;  // false : 컨트롤러 실행 중단.
        }
        return true;  // 컨트롤러 계속 진행
    }
}
