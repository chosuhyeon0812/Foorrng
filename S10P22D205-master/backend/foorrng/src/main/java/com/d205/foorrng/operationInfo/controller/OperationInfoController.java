package com.d205.foorrng.operationInfo.controller;


import com.d205.foorrng.common.model.BaseResponseBody;
import com.d205.foorrng.operationInfo.OperationInfo;
import com.d205.foorrng.operationInfo.dto.OperationInfoDto;
import com.d205.foorrng.operationInfo.service.OperationInfoService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter @Setter
@RestController
@RequestMapping("/api/oper")
@RequiredArgsConstructor
public class OperationInfoController {

    private final OperationInfoService operationInfoService;

    @PostMapping("/{mark-id}/regist")
    public ResponseEntity<? extends BaseResponseBody> postOperationInfo(@PathVariable("mark-id") Long markId,
                                                                        @RequestBody @Valid OperationInfoDto operationInfoDto) {
        System.out.println(operationInfoDto.getOperationInfoList());
        Map<String, Object> response = new HashMap<>();
        List<OperationInfo> operationInfoList = operationInfoService.createOperationInfo(markId, operationInfoDto);

        response.put("markId", markId);
        response.put("operationInfoList", operationInfoList);

        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponseBody.of(0, response));
    }


    @GetMapping("/{mark-id}")
    public ResponseEntity<? extends BaseResponseBody> getOperationInfoList(@PathVariable("mark-id") Long markId) {
        List<OperationInfo> operationInfoList = operationInfoService.searchOperationInfo(markId);
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(0, operationInfoList));
    }


    @PatchMapping("/{oper-id}")
    public ResponseEntity<? extends BaseResponseBody> patchOperationInfo(@PathVariable("oper-id") Long operId,
                                                                         @RequestBody @Valid OperationInfoDto operationInfoDto) {

        OperationInfo operationInfo = operationInfoService.updateOperationInfo(operId, operationInfoDto);


        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(0, operationInfo));
    }

    @DeleteMapping("/{oper-id}")
    public ResponseEntity<? extends BaseResponseBody> deleteOperationInfo(@PathVariable("oper-id") Long operId) {

        operationInfoService.removeOperationInfo(operId);

        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(0, "운영정보가 삭제되었습니다."));
    }



}
