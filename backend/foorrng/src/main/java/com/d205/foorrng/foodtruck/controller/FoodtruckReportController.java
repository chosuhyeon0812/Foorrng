package com.d205.foorrng.foodtruck.controller;

import com.d205.foorrng.common.model.BaseResponseBody;
import com.d205.foorrng.foodtruck.request.FoodtruckCreateReqDto;
import com.d205.foorrng.foodtruck.request.FoodtruckUpdateReqDto;
import com.d205.foorrng.foodtruck.response.FoodtruckRepResDto;
import com.d205.foorrng.foodtruck.service.FoodtruckReportService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/foodtruck-report")
@Validated
public class FoodtruckReportController {
    private final FoodtruckReportService foodtruckReportService;

    @PostMapping(value = "/regist", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiResponse(responseCode = "201", description = "제보 푸드트럭 등록 완료")
    public ResponseEntity<? extends BaseResponseBody> createFoodtruckReport(
            @Valid @RequestPart(value = "foodtruckDto") FoodtruckCreateReqDto foodtruckDto,
            @RequestPart(value = "picture", required = false) MultipartFile picture) throws IOException {
        FoodtruckRepResDto foodtruckRepResDto = foodtruckReportService.createFoodtruck(foodtruckDto, picture);
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponseBody.of(0,foodtruckRepResDto));
    }

    @PatchMapping(value = "/update", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiResponse(responseCode = "200", description = "제보 푸드트럭 수정 완료")
    public ResponseEntity<? extends BaseResponseBody> updateFoodtruckReport(
            @Valid @RequestPart(value = "foodtruckDto") FoodtruckUpdateReqDto foodtruckUpdateReqDto,
            @RequestPart(value = "picture", required = false) MultipartFile picture) throws IOException {
        FoodtruckRepResDto foodtruckRepResDto = foodtruckReportService.updateFoodtruck(foodtruckUpdateReqDto, picture);
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(0, foodtruckRepResDto));
    }

    @DeleteMapping("/delete/{foodtruckReportId}")
    @ApiResponse(responseCode = "200", description = "제보 푸드트럭 삭제 완료")
    public ResponseEntity<? extends BaseResponseBody> deleteFoodtruckReport(
            @Valid @PathVariable("foodtruckReportId") Long foodtruckId) throws IOException {
            int flag = foodtruckReportService.deleteFoodtruck(foodtruckId);
            if (flag==1){
                return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(0, "삭제 요청 추가 되었습니다"));
            }else{
                return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(0, "삭제 되었습니다"));
            }
    }
}
