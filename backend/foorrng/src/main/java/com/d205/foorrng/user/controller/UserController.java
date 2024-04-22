package com.d205.foorrng.user.controller;


import com.d205.foorrng.common.exception.ErrorCode;
import com.d205.foorrng.common.exception.Exceptions;
import com.d205.foorrng.common.model.BaseResponseBody;
import com.d205.foorrng.food.repository.FavoritefoodRepository;
import com.d205.foorrng.foodtruck.repository.FoodtruckLikeRepository;
import com.d205.foorrng.user.dto.RegistDto;
import com.d205.foorrng.user.entity.User;
import com.d205.foorrng.user.repository.UserRepository;
import com.d205.foorrng.user.repository.UserRole;
import com.d205.foorrng.user.service.UserRegistService;
import com.d205.foorrng.user.service.UserSignService;
import com.d205.foorrng.user.dto.UserDto;
import com.d205.foorrng.util.SecurityUtil;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;


@Getter @Setter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;
    private final UserSignService userSignService;
    private final FavoritefoodRepository favoritefoodRepository;
    private final FoodtruckLikeRepository foodtruckLikeRepository;
    private final UserRegistService userRegistService;

    @PostMapping("/regist/owner")
    public ResponseEntity<? extends BaseResponseBody> signUpOwner(@RequestBody @Valid UserDto userDto) {

        Long userUid = userSignService.signUp(userDto, "OWNER");

        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponseBody.of(0, userUid));
    }

    @PostMapping("/regist/user")
    public ResponseEntity<? extends BaseResponseBody> signUpUser(@RequestBody @Valid UserDto userDto) {

        Long userUid = userSignService.signUp(userDto, "USER");

        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponseBody.of(0, userUid));
    }


    @PostMapping("/login")
    public ResponseEntity<? extends BaseResponseBody> signIn(@RequestBody @Valid UserDto userDto) {

        Map<String, Object> response = userSignService.signIn(userDto);
//        Map<String, String> response = new HashMap<>();
//        response.put("accessToken", tokenDto.getAccessToken());
//        response.put("refreshToken", tokenDto.getRefreshToken());

        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(0, response));

    }

    @GetMapping("")
    public ResponseEntity<? extends BaseResponseBody> searchUserInfo() {

        Optional<User> user = userRepository.findByUserUid(Long.parseLong(SecurityUtil.getCurrentUsername()
                .orElseThrow(() -> new Exceptions(ErrorCode.USER_NOT_EXIST))));
        if (user.get().getRole().equals(UserRole.USER)) {
            // 소비자 정보 조회

            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("userUid", SecurityUtil.getCurrentUsername());

            userInfo.put("FavoriteFoodList", favoritefoodRepository.findAllByUserAndCreatedTime(user.get(), LocalDate.now(ZoneId.of("Asia/Seoul")).toString()).get()
                    .stream()
                    .map(favoriteFoods -> favoriteFoods.getMenu())
                    .collect(Collectors.toList()));
//            userInfo.put("FoodtruckLikeList", foodtruckLikeRepository.findAllByUser(user.get()));

            return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(0, userInfo));
        }
        if (user.get().getRole().equals(UserRole.OWNER)) {
            // 점주 정보 조회

            Map<String, String> userInfo = new HashMap<>();
            userInfo.put("userUid", SecurityUtil.getCurrentUsername().get());
            userInfo.put("name", user.get().getName());
            userInfo.put("email", user.get().getEmail());

            return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(0, userInfo));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(BaseResponseBody.error(ErrorCode.USER_NOT_EXIST.getErrorCode(),
                        ErrorCode.USER_NOT_EXIST.getMessage()));
    }


    @PostMapping("/certifyId")
    public ResponseEntity<? extends BaseResponseBody> cerfify(@RequestBody @Valid RegistDto businessNumber) {

        if (businessNumber.getBusinessNumber().equals("0123456789")) {
            userRegistService.testMethod();
            return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(0, "사업자등록번호가 성공적으로 등록되었습니다."));
        }
        if (!userRegistService.checkBusinessNumber(businessNumber)) {
            throw new Exceptions(ErrorCode.BUSINESSNUMBER_NOT_VALIDATE);
        }
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(0, "사업자등록번호가 성공적으로 등록되었습니다."));

    }


}
