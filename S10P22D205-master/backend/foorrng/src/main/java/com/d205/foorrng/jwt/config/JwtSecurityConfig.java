package com.d205.foorrng.jwt.config;

import com.d205.foorrng.jwt.token.JwtFilter;
import com.d205.foorrng.jwt.token.TokenProvider;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

//    private final TokenProvider tokenProvider;
//
//    public JwtSecurityConfig(TokenProvider tokenProvider) {
//        this.tokenProvider = tokenProvider;
//    }
//
//    @Override
//    public void configure(HttpSecurity http) {
//        http.addFilterBefore(
//                new JwtFilter(tokenProvider),
//                UsernamePasswordAuthenticationFilter.class
//        );
//    }
}
