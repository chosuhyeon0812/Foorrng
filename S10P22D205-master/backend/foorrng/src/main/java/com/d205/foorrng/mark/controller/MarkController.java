package com.d205.foorrng.mark.controller;


import com.d205.foorrng.common.model.BaseResponseBody;
import com.d205.foorrng.mark.Mark;
import com.d205.foorrng.mark.dto.MarkDto;
import com.d205.foorrng.mark.dto.MarkReqDto;
import com.d205.foorrng.mark.service.MarkService;
import com.d205.foorrng.operationInfo.OperationInfo;
import com.d205.foorrng.operationInfo.dto.OperationInfoDto;
import com.d205.foorrng.operationInfo.service.OperationInfoService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

@Getter @Setter
@RestController
@RequestMapping("/api/mark")
@RequiredArgsConstructor
public class MarkController {


    private final MarkService markService;
    private final OperationInfoService operationInfoService;

    // 마커 생성
    @PostMapping("/{foodtruck-id}/regist")
    public ResponseEntity<? extends BaseResponseBody> postMark(@PathVariable("foodtruck-id") Long foodtruckId,
                                                               @RequestBody @Valid MarkReqDto markReqDto) {

        Map<String, Object> response = markService.createMark(foodtruckId, markReqDto.getMarkDto());
        List<OperationInfo> operationInfoList = operationInfoService.createOperationInfo(Long.parseLong(response.get("markId").toString()), markReqDto.getOperationInfoDto());
        response.put("operationInfoList", operationInfoList);

        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponseBody.of(0, response));
    }


    // 마커 상세 조회
    @GetMapping("/{mark-id}")
    public ResponseEntity<? extends BaseResponseBody> getMarkDetail(@PathVariable("mark-id") Long markId) {

        Mark mark = markService.searchMarkDetail(markId);

        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(0, mark));
    }



    // 마커 수정
    @PatchMapping("/{mark-id}")
    public ResponseEntity<? extends BaseResponseBody> patchMark(@PathVariable("mark-id") Long markId,
                                                                @RequestBody @Valid MarkDto markDto) {

        Mark mark = markService.updateMark(markId, markDto);

        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(0, mark));
    }


    // 마커 삭제
    @DeleteMapping("/{mark-id}")
    public ResponseEntity<? extends BaseResponseBody> patchMark(@PathVariable("mark-id") Long markId) {
        markService.removeMark(markId);

        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(0, "마커가 삭제되었습니다."));
    }



    // 마커 조회 ( 점주 운영 상태 )
    @GetMapping("/list")
    public ResponseEntity<? extends BaseResponseBody> getOwnerMarkList() {

        List<Mark> markList = markService.searchOwnerMarkList();

        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(0, markList));
    }


    // 마크 운영상태
    @GetMapping("/{mark-id}/change")
    public ResponseEntity<? extends BaseResponseBody> changeMarkOperation(@PathVariable("mark-id") Long markId) {

        Mark mark = markService.toggleMark(markId);

        return  ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(0, mark));
    }


}
