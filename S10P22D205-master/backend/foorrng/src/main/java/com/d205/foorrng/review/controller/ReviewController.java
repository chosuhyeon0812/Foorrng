package com.d205.foorrng.review.controller;

import com.d205.foorrng.common.model.BaseResponseBody;
import com.d205.foorrng.foodtruck.repository.FoodtrucksRepository;
import com.d205.foorrng.review.request.ReviewReqDto;
import com.d205.foorrng.review.response.ReviewResDto;
import com.d205.foorrng.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
@Validated
public class ReviewController {
    private final ReviewService reviewService;
    private final FoodtrucksRepository foodtrucksRepository;

    @PostMapping(value = "/{foodtruckId}/regist")
    @ApiResponse(responseCode = "201", description = "리뷰 생성 성공")
    public ResponseEntity<? extends BaseResponseBody> createReview(
            @PathVariable("foodtruckId") Long foodtrucksSeq,
            @Valid @Parameter(name ="reviewReqDto", description = "리뷰 정보") @RequestBody ReviewReqDto reviewReqDto){

        ReviewResDto reviewId = reviewService.createReview(foodtrucksSeq, reviewReqDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponseBody.of(0, reviewId));
    }

    @GetMapping("/{foodtruckId}/list")
    @ApiResponse(responseCode = "200", description = "리뷰 리스트 조회 성공")
    public ResponseEntity<? extends BaseResponseBody> getReview(@PathVariable("foodtruckId") Long foodtruckSeq){
     return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(0, reviewService.getReviews(foodtruckSeq)));
    }
}
