package com.d205.foorrng.bigdata.controller;


import com.d205.foorrng.bigdata.service.BigdataService;
import com.d205.foorrng.common.model.BaseResponseBody;
import com.d205.foorrng.food.service.FoodService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommend")
public class BigdataController {
    private final BigdataService bigdataService;
    private final FoodService foodService;

    @GetMapping("")
    @ApiResponse(responseCode = "200", description = "위치 추천")
    public ResponseEntity<? extends BaseResponseBody> recommendPosition(){
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(0, bigdataService.recommendPosition()));
    }

//    @Scheduled(cron = "* 10 * * * *", zone = "Asia/Seoul")
//    // schedule 를 통해 일정 시간에 유저 선호도 조사 데이터를 갱신
//    // user 가 선호도 조사를 진행한 동 이름과 메뉴이름을 csv 파일화
//    @GetMapping("/update")
//    @ApiResponse(responseCode = "200", description = "유저 선호도 조사 데이터 업데이트")
//    public ResponseEntity<? extends BaseResponseBody> updateSurveyData(){
//        bigdataService.saveDataToCsv();
//        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(0, "유저 선호도 조사 데이터 업데이트 완료."));
//    }


}
