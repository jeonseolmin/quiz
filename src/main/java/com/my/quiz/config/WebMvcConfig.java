package com.my.quiz.config;

import com.my.quiz.interceptor.MyInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final MyInterceptor myInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 어느 url 요청 시에 가로챌건데?
        registry
                .addInterceptor(myInterceptor)
                // member/mypage 및 그 아래 폴더 전체를 다 잡겠다.
                .addPathPatterns("/member/mypage/**", "/member/list")
                .excludePathPatterns("/member/login",
                        "/member/logout",
                        "/css/**",
                        "/js/**");
    }
}
