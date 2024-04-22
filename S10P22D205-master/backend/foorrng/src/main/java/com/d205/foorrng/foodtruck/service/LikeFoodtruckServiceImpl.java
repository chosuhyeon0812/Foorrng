package com.d205.foorrng.foodtruck.service;

import com.d205.foorrng.common.exception.ErrorCode;
import com.d205.foorrng.common.exception.Exceptions;
import com.d205.foorrng.foodtruck.entity.FoodtruckLike;
import com.d205.foorrng.foodtruck.entity.Foodtrucks;
import com.d205.foorrng.foodtruck.repository.FoodtruckLikeRepository;
import com.d205.foorrng.foodtruck.repository.FoodtrucksRepository;
import com.d205.foorrng.foodtruck.response.LikeFoodtrucksDto;
import com.d205.foorrng.user.entity.User;
import com.d205.foorrng.user.repository.UserRepository;
import com.d205.foorrng.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeFoodtruckServiceImpl implements LikeFoodtruckService {
    private final UserRepository userRepository;
    private final FoodtrucksRepository foodtrucksRepository;
    private final FoodtruckLikeRepository foodtruckLikeRepository;

    @Override
    public void likeFoodtruck(Long foodtrucks_seq) {
        User user = userRepository.findByUserUid(Long.parseLong(SecurityUtil.getCurrentUsername().get())).get();

        Foodtrucks foodtrucks = foodtrucksRepository.findById(foodtrucks_seq)
                .orElseThrow(() -> new Exceptions(ErrorCode.FOODTRUCK_NOT_EXIST));

        Optional<FoodtruckLike> foodtruckLike = foodtruckLikeRepository.findByUserAndFoodtrucks(user, foodtrucks);

        if(foodtruckLike.isPresent()) { // 좋아요가 이미 존재 -> 취소
            foodtruckLikeRepository.delete(foodtruckLike.get());
        } else {     // 좋아요가 없음 -> 생성
            foodtruckLikeRepository.save(new FoodtruckLike(user, foodtrucks));
        }
    }
}
