package com.d205.foorrng.operationInfo.service;


import com.d205.foorrng.common.exception.ErrorCode;
import com.d205.foorrng.common.exception.Exceptions;
import com.d205.foorrng.mark.Mark;
import com.d205.foorrng.mark.dto.MarkReqDto;
import com.d205.foorrng.mark.repository.MarkRepository;
import com.d205.foorrng.operationInfo.OperationInfo;
import com.d205.foorrng.operationInfo.dto.OperationInfoDto;
import com.d205.foorrng.operationInfo.repository.OperationInfoRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Getter @Setter
@RequiredArgsConstructor
public class OperationInfoService {

    private final MarkRepository markRepository;
    private final OperationInfoRepository operationInfoRepository;

    private final ArrayList<String> days = new ArrayList<>(Arrays.asList("월", "화", "수", "목", "금", "토", "일"));
//    @Transactional
    public List<OperationInfo> createOperationInfo(Long markId, OperationInfoDto operationInfoDto) {

        Mark mark = markRepository.findById(markId)
                .orElseThrow(() -> new Exceptions(ErrorCode.MARK_NOT_EXIST));

        Set<String> allDays = operationInfoRepository.findAllDaysByFoodTruckId(mark.getFoodtrucks().getId());

        for (Map<String, Object> day : operationInfoDto.getOperationInfoList()) {
            if (allDays.contains(day.get("day"))) {
                if (mark.getOperationInfoList() == null || mark.getOperationInfoList().isEmpty()) {
                    markRepository.delete(mark);
                };
                throw new Exceptions(ErrorCode.DAY_OCCUPIED);
            }
            if (!days.contains(day.get("day"))) {
                if (mark.getOperationInfoList() == null || mark.getOperationInfoList().isEmpty()) {
                    markRepository.delete(mark);
                };
                throw new Exceptions(ErrorCode.DAY_NOT_VALID);
            }
            OperationInfo operationInfo = OperationInfo
                    .builder()
                    .mark(mark)
                    .day(day.get("day").toString())
                    .startTime(LocalTime.ofInstant(
                            Instant.ofEpochMilli(Long.parseLong(day.get("startTime").toString())),
                            ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("HH:mm")))
                    .endTime(LocalTime.ofInstant(
                            Instant.ofEpochMilli(Long.parseLong(day.get("endTime").toString())),
                            ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("HH:mm")))
                    .build();

            operationInfoRepository.save(operationInfo);
        }
        List<OperationInfo> operationResponse = operationInfoRepository.findAllByMarkId(markId)
                .orElseThrow(() -> new Exceptions(ErrorCode.OPERATION_NOT_EXIST));

        return operationResponse;
    }

    @Transactional
    public List<OperationInfo> searchOperationInfo(Long markId) {

        Mark mark = markRepository.findById(markId)
                .orElseThrow(() -> new Exceptions(ErrorCode.MARK_NOT_EXIST));

        List<OperationInfo> operationInfoList = operationInfoRepository.findAllByMarkId(mark.getId())
                .orElseThrow(() -> new Exceptions(ErrorCode.OPERATION_NOT_EXIST));

        return operationInfoList;
    }


    @Transactional
    public OperationInfo updateOperationInfo(Long operId, OperationInfoDto operationInfoDto) {

        OperationInfo operationInfo = operationInfoRepository.findById(operId)
                .orElseThrow(() -> new Exceptions(ErrorCode.OPERATION_NOT_EXIST));

        Mark mark = markRepository.findById(operationInfo.getMark().getId())
                .orElseThrow(() -> new Exceptions(ErrorCode.MARK_NOT_EXIST));

        Set<String> allDays = operationInfoRepository.findAllDaysByFoodTruckId(mark.getFoodtrucks().getId());
        if (allDays.contains(operationInfoDto.getOperationInfoList().get(0).get("day").toString())) {
            throw new Exceptions(ErrorCode.DAY_OCCUPIED);
        }

        operationInfo.update(operationInfoDto);

        operationInfoRepository.save(operationInfo);

        return operationInfo;
    }


    @Transactional
    public void removeOperationInfo(Long operId) {

        OperationInfo operationInfo = operationInfoRepository.findById(operId)
                .orElseThrow(() -> new Exceptions(ErrorCode.OPERATION_NOT_EXIST));

        operationInfoRepository.delete(operationInfo);
    }


}
