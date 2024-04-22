package com.d205.foorrng.jwt.cotroller;


import com.d205.foorrng.jwt.token.JwtFilter;
import com.d205.foorrng.jwt.token.TokenDto;
import com.d205.foorrng.jwt.token.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Getter @Setter
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class AuthController {

    private final TokenProvider tokenProvider;
    private final UserDetailsService userDetailsService;

    private String resolveTokenFromHeader(HttpServletRequest request, String headerName) {
        String bearerToken = request.getHeader(headerName);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }


//    @PostMapping("/token/refresh")
//    public ResponseEntity<?> regenerateAccessToken(HttpServletRequest request) {
//        String accessToken = resolveTokenFromHeader(request, "Access-Token");
//        String refreshToken = resolveTokenFromHeader(request, "Refresh-Token");
//
//        try {
//            if (!tokenProvider.validateToken(refreshToken, "refresh")) {
//                return ResponseEntity.badRequest().body("유효하지 않거나 만료된 토큰입니다.");
//            }
//
//            if (!tokenProvider.validateToken(accessToken, "access") && !tokenProvider.validateTokenWithoutExpiration(accessToken)) {
//                return ResponseEntity.badRequest().body("유효하지 않은 토큰입니다.");
//            }
//
//            UserDetails userDetails = tokenProvider.getUserDetailsFromToken(refreshToken);
//            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//            TokenDto newAccessToken = tokenProvider.createToken(authentication);
//
//            return ResponseEntity.ok(newAccessToken);
//        } catch (Exception e) {
//            return ResponseEntity.internalServerError().body(e);
//        }
//
//
//    }

}
