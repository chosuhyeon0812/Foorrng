package com.d205.foorrng.foodtruck.controller;

import com.d205.foorrng.common.model.BaseResponseBody;
import com.d205.foorrng.foodtruck.request.FoodtruckCreateReqDto;
import com.d205.foorrng.foodtruck.request.FoodtruckUpdateReqDto;
import com.d205.foorrng.foodtruck.response.FoodtruckResDto;
import com.d205.foorrng.foodtruck.service.FoodtruckService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

//@Slf4j // log
@RestController // rest api
@RequiredArgsConstructor // 생성자
@RequestMapping("/api/foodtruck") // url map
//@Validated  // 유효성 검증
public class FoodtruckController {
    private final FoodtruckService foodtruckService;

    @ApiResponse(responseCode = "201", description = "점주 푸드트럭 등록 완료")
    @PostMapping(value = "/regist", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<? extends BaseResponseBody> createFoodtruck(
            @Valid @RequestPart(value = "foodtruckDto") FoodtruckCreateReqDto foodtruckDto,
            @RequestPart(value = "picture", required = false) MultipartFile picture) throws IOException {
        FoodtruckResDto foodtruckResDto = foodtruckService.createFoodtruck(foodtruckDto, picture);
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponseBody.of(0, foodtruckResDto));
    }

    @PatchMapping(value = "/update", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiResponse(responseCode = "200", description = "점주 푸드트럭 수정 완료")
    public ResponseEntity<? extends BaseResponseBody> updateFoodtruck(
            @Valid @RequestPart(value = "foodtruckDto") FoodtruckUpdateReqDto foodtruckDto,
            @RequestPart(value = "picture", required = false) MultipartFile picture) throws IOException{
        FoodtruckResDto foodtruckResDto = foodtruckService.updateFoodtruck(foodtruckDto, picture);
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(0, foodtruckResDto));
    }


    @DeleteMapping("/delete/{foodtruckId}")
    @ApiResponse(responseCode = "201", description = "점주 푸드트럭 삭제 완료")
    public ResponseEntity<? extends BaseResponseBody> deleteFoodtruck(@Valid @PathVariable("foodtruckId") Long foodtruckId){
        foodtruckService.deleteFoodtruck(foodtruckId);
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(0, "success"));
    }
}
