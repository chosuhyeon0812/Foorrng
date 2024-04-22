package com.d205.foorrng.foodtruck.service;

import com.d205.foorrng.foodtruck.entity.FoodtruckReportId;
import com.d205.foorrng.foodtruck.request.FoodtruckCreateReqDto;
import com.d205.foorrng.foodtruck.request.FoodtruckUpdateReqDto;
import com.d205.foorrng.foodtruck.response.FoodtruckRepResDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FoodtruckReportService {

    FoodtruckRepResDto createFoodtruck(FoodtruckCreateReqDto foodtruckCreateReqDto, MultipartFile picture) throws IOException;
    FoodtruckRepResDto updateFoodtruck(FoodtruckUpdateReqDto foodtruckUpdateReqDto, MultipartFile picture) throws IOException;
    int deleteFoodtruck(Long foodtruckId) throws IOException;
}
