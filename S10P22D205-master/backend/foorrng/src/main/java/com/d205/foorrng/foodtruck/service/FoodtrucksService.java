package com.d205.foorrng.foodtruck.service;

import com.d205.foorrng.foodtruck.request.FoodtrucksReqDto;
import com.d205.foorrng.foodtruck.response.FoodtrucksResDto;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface FoodtrucksService {
    List<FoodtrucksResDto> foodtrucklist(FoodtrucksReqDto foodtrucksReqDto);
    Map<String, Object> foodtruckdetail(Long foodtrucksId) throws IOException;
    Map<String, Object> myfoodtruck();
    List<Map<String, Object>> myfoodtruckOper();

}
