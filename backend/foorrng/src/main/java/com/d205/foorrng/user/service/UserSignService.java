package com.d205.foorrng.user.service;

import com.d205.foorrng.common.exception.ErrorCode;
import com.d205.foorrng.common.exception.Exceptions;
import com.d205.foorrng.food.repository.FavoritefoodRepository;
import com.d205.foorrng.jwt.token.TokenDto;
import com.d205.foorrng.jwt.token.TokenProvider;
import com.d205.foorrng.user.dto.UserDto;
import com.d205.foorrng.user.entity.FavoriteFood;
import com.d205.foorrng.user.entity.User;
import com.d205.foorrng.user.repository.UserRepository;
import com.d205.foorrng.user.repository.UserRole;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class UserSignService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final FavoritefoodRepository favoritefoodRepository;

    @Transactional
    public Long signUp(UserDto userDto, String role) {

        if (userRepository.findByUserUid(userDto.getUserUid()).isPresent()) {
            throw new Exceptions(ErrorCode.EMAIL_EXIST);
        }
        User user = User.builder()
                .userUid(userDto.getUserUid())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .role(UserRole.valueOf(role))
                .build();
        Long userUid = userRepository.save(user).getUserUid();

        return userUid;
    }

    @Transactional
    public Map<String, Object> signIn(UserDto userDto) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDto.getUserUid(), "");

        User user = userRepository.findByUserUid(userDto.getUserUid())
                .orElseThrow(() -> new Exceptions(ErrorCode.USER_NOT_EXIST));

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);


        TokenDto tokenDto = tokenProvider.createToken(authentication);


        Map<String, Object> response = new HashMap<>();

        response.put("accessToken", tokenDto.getAccessToken());
        response.put("serveyCheck", serveyCheck(userDto.getUserUid()));
        response.put("refreshToken", tokenDto.getRefreshToken());
        response.put("businessNumber", user.getBusinessNumber() ==null ?false:true);

        return response;
    }

    public Boolean serveyCheck(Long userUid) {
        User user = userRepository.findByUserUid(userUid)
                .orElseThrow(() -> new Exceptions(ErrorCode.USER_NOT_EXIST));
       List<FavoriteFood> favoriteFoodList = favoritefoodRepository.findAllByUserAndCreatedTime(user, LocalDate.now(ZoneId.of("Asia/Seoul")).toString()).get();
        if (favoriteFoodList.size() >= 1) {
            return true;
        }
        return false;
    }
}
