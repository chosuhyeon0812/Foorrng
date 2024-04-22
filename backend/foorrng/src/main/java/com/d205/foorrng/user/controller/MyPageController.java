package com.d205.foorrng.user.controller;

import com.d205.foorrng.common.model.BaseResponseBody;
import com.d205.foorrng.foodtruck.response.FoodtrucksUserResDto;
import com.d205.foorrng.user.service.MyPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
@Validated
public class MyPageController {
    private final MyPageService myPageService;

    @GetMapping("/likes")
    public ResponseEntity<? extends BaseResponseBody> getLikeFoodtrucks() {
        List<FoodtrucksUserResDto> likedFoodtrucks = myPageService.getLikeFoodtrucks();
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(0, likedFoodtrucks));
    }
}
