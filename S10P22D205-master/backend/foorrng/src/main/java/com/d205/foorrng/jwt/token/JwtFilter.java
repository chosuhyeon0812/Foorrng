package com.d205.foorrng.jwt.token;

import com.d205.foorrng.common.exception.JwtAuthenticationEntryPoint;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@Getter @Setter
@Slf4j
@RequiredArgsConstructor
@AllArgsConstructor
public class JwtFilter extends GenericFilterBean {

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    private TokenProvider tokenProvider;

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String accessToken = resolveToken(httpServletRequest, "Authorization");
        String refreshToken = resolveToken(httpServletRequest, "Refresh-Token");
        String requestURI = httpServletRequest.getRequestURI();

        try {
            if (isAllowedPath(requestURI)) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
            if ("/api/user/token/refresh".equals(requestURI)) {
                if (refreshToken != null && tokenProvider.validateToken(refreshToken, "refresh")) {
                    filterChain.doFilter(servletRequest, servletResponse);
                } else {
                    sendUnauthorizedResponse(httpServletResponse, "Refresh Token이 유효하지 않거나 만료되었습니다. 다시 로그인해주세요.");
                }
            } else {
                if (accessToken != null && tokenProvider.validateToken(accessToken, "access")) {
                    Authentication authentication = tokenProvider.getAuthentication(accessToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    filterChain.doFilter(servletRequest, servletResponse);
                } else {
                    sendUnauthorizedResponse(httpServletResponse, "Access Token이 유효하지 않습니다. 다시 로그인해주세요.");
                }

            }
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            logger.error("Authentication error: ", e);
            sendUnauthorizedResponse(httpServletResponse, "Authentication error: " + e.getMessage());
        }


//        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
//            Authentication authentication = tokenProvider.getAuthentication(jwt);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            logger.debug("Security Context 에 '{}' 인증정보 저장 완료. uri : {}", authentication.getName(), requestURI);
//        } else {
//            logger.debug("유효한 jwt 토큰이 없음. uri : {}", requestURI);
//        }
//
//        filterChain.doFilter(servletRequest, servletResponse);
    }


    private boolean isAllowedPath(String requestUri) {
        List<String> allowedPaths = Arrays.asList("/api/user/regist/", "/api/user/login", "/swagger-ui/", "/v3/");
        return allowedPaths.stream().anyMatch(path -> requestUri.startsWith(path));
    }

    private String resolveToken(HttpServletRequest request, String headerName) {

        String bearerToken = request.getHeader(headerName);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    private void sendUnauthorizedResponse(HttpServletResponse response, String message) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(message);
    }
}
