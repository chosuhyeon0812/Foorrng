package com.d205.foorrng.foodtruck.service;

import com.d205.foorrng.foodtruck.response.LikeFoodtrucksDto;

public interface LikeFoodtruckService {
    // 푸드트럭 찜하기
    void likeFoodtruck(Long foodtrucks_seq);
}
