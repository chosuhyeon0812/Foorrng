package com.d205.foorrng.foodtruck.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.List;

@Getter
public class FoodtruckCreateDto {

    // 푸드트럭 정보
    private String announcement;            // 공지사항
    @NotEmpty
    private String name;                    // 가게 이름
    private String accountInfo;            // 계봐 번호
    @NotEmpty
    private String carNumber;              // 차량 번호
    private String phoneNumber;            // 연락처

    // 메뉴 정보
    List<MenuRequestDto> menuRequestDtoList;
}
