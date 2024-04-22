package com.d205.foorrng.user.service;

import com.d205.foorrng.common.exception.ErrorCode;
import com.d205.foorrng.common.exception.Exceptions;
import com.d205.foorrng.food.Food;
import com.d205.foorrng.food.repository.FoodRepository;
import com.d205.foorrng.foodtruck.entity.*;
import com.d205.foorrng.foodtruck.repository.FoodtruckLikeRepository;
import com.d205.foorrng.foodtruck.repository.FoodtruckReportRepository;
import com.d205.foorrng.foodtruck.repository.FoodtruckRepository;
import com.d205.foorrng.foodtruck.response.FoodtrucksUserResDto;
import com.d205.foorrng.review.entity.Review;
import com.d205.foorrng.review.repository.ReviewRepository;
import com.d205.foorrng.user.entity.User;
import com.d205.foorrng.user.repository.UserRepository;
import com.d205.foorrng.util.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {
    private final UserRepository userRepository;
    private final FoodRepository foodRepository;
    private final ReviewRepository reviewRepository;
    private final FoodtruckRepository foodtruckRepository;
    private final FoodtruckLikeRepository foodtruckLikeRepository;
    private final FoodtruckReportRepository foodtruckReportRepository;

    @Override
    public List<FoodtrucksUserResDto> getLikeFoodtrucks() {
        User user = userRepository.findByUserUid(Long.parseLong(SecurityUtil.getCurrentUsername().get())).get();

        List<FoodtruckLike> likes = foodtruckLikeRepository.findAllByUser(user).get();
        List<FoodtrucksUserResDto> foodtrucksResDtoList = new ArrayList<>();

        for (FoodtruckLike foodtruckLike : likes) {
            Foodtrucks foodtrucks = foodtruckLike.getFoodtrucks();

            String foodtruckName;
            String foodtruckPicture;
            if (foodtrucks.getFoodtruckRole().equals(FoodtruckRole.Foodtruck)) {
                Foodtruck foodtruck = foodtruckRepository.findByFoodtruckId(new FoodtruckId(foodtrucks.getId()))
                        .orElseThrow(() -> new Exceptions(ErrorCode.FOODTRUCK_NOT_EXIST));
                foodtruckName = foodtruck.getName();
                foodtruckPicture = foodtruck.getPicture();
            } else {
                FoodtruckReport foodtruckReport = foodtruckReportRepository.findByFoodtruckId(new FoodtruckReportId(foodtrucks.getId()))
                        .orElseThrow(() -> new Exceptions(ErrorCode.FOODTRUCK_NOT_EXIST));
                foodtruckName = foodtruckReport.getName();
                foodtruckPicture = foodtruckReport.getPicture();
            }

            // 음식카테고리
            List<Food> foods = foodRepository.findAllByFoodtrucks(foodtrucks);
            List<String> category = new ArrayList<>();
            for (Food food:foods) {
                category.add(food.getName());
            }

            // 리뷰 총 개수
            List<Review> reviews = reviewRepository.findByFoodtrucksId(foodtrucks.getId());

            FoodtrucksUserResDto foodtrucksResDto = new FoodtrucksUserResDto(foodtrucks.getId(), foodtrucks.getFoodtruckRole(), foodtruckName, foodtruckPicture, category, reviews.size());
            foodtrucksResDtoList.add(foodtrucksResDto);
        }
            return foodtrucksResDtoList;
    }
}
