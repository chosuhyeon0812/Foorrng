package com.d205.foorrng.foodtruck.response;

import com.d205.foorrng.foodtruck.entity.FoodtruckReport;
import lombok.Getter;
import java.util.List;

@Getter
public class FoodtruckRepResDto {
    private Long foodtruckId;
    private String announcement;            // 공지사항
    private Long createdDay;               // 등록일
    private String picture;                 // 푸듣트럭 차 사진
    private String name;                    // 가게 이름
    private String accountInfo;            // 계봐 번호
    private String carNumber;              // 차량 번호
    private String phoneNumber;            // 연락처
    private List<String> category;
    private String businessNumber;


    public FoodtruckRepResDto(FoodtruckReport foodtruckReport, Long id, List<String> category, String businessNumber){
        this.foodtruckId = id;
        this.announcement = foodtruckReport.getAnnouncement();
        this.createdDay = foodtruckReport.getCreatedDay();
        this.picture = foodtruckReport.getPicture();
        this.name = foodtruckReport.getName();
        this.accountInfo = foodtruckReport.getAccountInfo();
        this.carNumber = foodtruckReport.getCarNumber();
        this.phoneNumber = foodtruckReport.getPhoneNumber();
        this.category = category;
        this.businessNumber = businessNumber;
    }

    public FoodtruckRepResDto(FoodtruckReport foodtruck) {
        this.announcement = foodtruck.getAnnouncement();
        this.picture = foodtruck.getPicture();
        this.name = foodtruck.getName();
        this.accountInfo = foodtruck.getAccountInfo();
        this.carNumber = foodtruck.getCarNumber();
        this.phoneNumber = foodtruck.getPhoneNumber();
    }
}
