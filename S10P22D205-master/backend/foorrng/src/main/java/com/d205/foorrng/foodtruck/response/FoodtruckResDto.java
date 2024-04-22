package com.d205.foorrng.foodtruck.response;

import com.d205.foorrng.foodtruck.entity.Foodtruck;
import lombok.Getter;
import java.util.List;

@Getter
public class FoodtruckResDto {
    private final Long foodtruckId;
    private String announcement;
    private Long createdDay;
    private String picture;
    private String name;
    private String accountInfo;
    private String carNumber;
    private String phoneNumber;
    private List<String> category;
    private String businessNumber;

//    private List<FoodtrucksResDto> foodtrucksResDtoList; // 추가 하는부분..

    // 생성
    public FoodtruckResDto(Foodtruck foodtruck, Long id, List<String> category, String businessNumber){
        this.foodtruckId = id;
        this.announcement = foodtruck.getAnnouncement();
        this.createdDay = foodtruck.getCreatedDay();
        this.picture = foodtruck.getPicture();
        this.name = foodtruck.getName();
        this.accountInfo = foodtruck.getAccountInfo();
        this.carNumber = foodtruck.getCarNumber();
        this.phoneNumber = foodtruck.getPhoneNumber();
        this.category = category;
        this.businessNumber = businessNumber;
    }


//    public FoodtruckResDto(Long foodtruckId, String announcement, String accountInfo, String carNumber, String phoneNumber, String name, String picture) {
//        this.footruckId = foodtruckId;
//        this.announcement = announcement;
//        this.picture = picture;
//        this.name = name;
//        this.accountInfo = accountInfo;
//        this.carNumber = carNumber;
//        this.phoneNumber = phoneNumber;
//    }
//
//    public static FoodtruckResDto fromEntity(Foodtruck foodtruck) {
//        return new FoodtruckResDto(
//                foodtruck.getFoodtrucks().getId(),
//                foodtruck.getAnnouncement(),
//                foodtruck.getAccountInfo(),
//                foodtruck.getPhoneNumber(),
//                foodtruck.getCarNumber(),
//                foodtruck.getName(),
//                foodtruck.getPicture()
//        );
//    }

}
