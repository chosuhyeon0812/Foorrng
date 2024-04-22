package com.d205.foorrng.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry corsRegistry){
        // 도메인 주소 입력하기
        List<String> origins = List.of();

        // CORS 설정을 모든 경로에 대해서 적용
        corsRegistry.addMapping("/**")
                // 오리진(도메인)에서 온 요청을 허용
                .allowedOrigins(String.join(",", origins))
                .allowedMethods("*");
    }
}
