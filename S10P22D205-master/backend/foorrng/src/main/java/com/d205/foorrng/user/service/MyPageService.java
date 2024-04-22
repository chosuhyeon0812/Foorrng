package com.d205.foorrng.user.service;

import com.d205.foorrng.foodtruck.response.FoodtrucksUserResDto;

import java.util.List;

public interface MyPageService {
    // 내가 찜한 푸드트럭 조회
    List<FoodtrucksUserResDto> getLikeFoodtrucks();
}
