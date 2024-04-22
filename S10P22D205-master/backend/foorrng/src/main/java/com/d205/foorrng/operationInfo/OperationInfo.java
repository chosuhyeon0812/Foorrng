package com.d205.foorrng.operationInfo;

import com.d205.foorrng.mark.Mark;
import com.d205.foorrng.operationInfo.dto.OperationInfoDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

@Entity
@Getter
@Builder
@Validated
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OperationInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_seq")
    @JsonIgnore
    private Long id;

    private String day;             // 요일

    private String startTime;        // 시작 시간

    private String endTime;          // 종료 시간

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mark_seq")
    @JsonIgnore
    private Mark mark;


    public void update(OperationInfoDto operationInfoDto) {
        this.day = operationInfoDto.getOperationInfoList().get(0).get("day").toString();
        this.startTime = LocalTime.ofInstant(
                Instant.ofEpochMilli(Long.parseLong(operationInfoDto.getOperationInfoList().get(0).get("startTime").toString())),
                ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("HH:mm"));
        this.endTime = LocalTime.ofInstant(
                Instant.ofEpochMilli(Long.parseLong(operationInfoDto.getOperationInfoList().get(0).get("endTime").toString())),
                ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("HH:mm"));


    }

}
