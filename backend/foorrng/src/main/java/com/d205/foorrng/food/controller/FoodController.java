package com.d205.foorrng.food.controller;


import com.d205.foorrng.common.model.BaseResponseBody;
import com.d205.foorrng.food.dto.FavoritefoodDto;
import com.d205.foorrng.food.repository.FavoritefoodList;
import com.d205.foorrng.food.repository.FavoritefoodRepository;
import com.d205.foorrng.food.service.FoodService;
import com.d205.foorrng.user.entity.FavoriteFood;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter @Setter
@RestController
@RequestMapping("/api/food")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;
    private final FavoritefoodRepository favoritefoodRepository;

    @PostMapping("/favorite")
    public ResponseEntity<? extends BaseResponseBody> postFavoriteFoods(@RequestBody @Valid FavoritefoodDto favoritefoodDto) {

        foodService.saveFavoriteFood(favoritefoodDto);
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(0, null));

    }


    @GetMapping("/favorite")
    public ResponseEntity<? extends BaseResponseBody> getFavoriteFoodsList() {

        Map<String, List<String>> response = new HashMap<>();
        List<String> favoritefoodList = Arrays.stream(FavoritefoodList.values()).map(FavoritefoodList::getMenu).toList();
        response.put("favoritefoodList", favoritefoodList);

        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(0, response));
    }

    @PatchMapping("/favorite")
    public ResponseEntity<? extends BaseResponseBody> patchFavoriteFoodList(@RequestBody @Valid FavoritefoodDto favoritefoodDto) {

        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(0, foodService.updateFavoriteFood(favoritefoodDto)));
    }
}
