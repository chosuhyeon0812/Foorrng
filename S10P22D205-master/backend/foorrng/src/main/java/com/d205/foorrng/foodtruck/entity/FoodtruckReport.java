package com.d205.foorrng.foodtruck.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Entity
@Getter
@Validated
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodtruckReport {
    @EmbeddedId
    private FoodtruckReportId foodtruckId;
    private String name;
    private Long createdDay;
    private String picture;
    private String carNumber;
    private String announcement;
    private String accountInfo;
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "foodtruck_id", referencedColumnName = "foodtrucks_seq")
    private Foodtrucks foodtrucks;


    @Builder
    public FoodtruckReport(FoodtruckReportId reportId, String name, String picture, String carNumber, String announcement, String accountInfo, String phoneNumber, Long createdDay, Foodtrucks foodtrucks){
        this.foodtruckId = reportId;
        this.name = name;
        this.picture = picture;
        this.carNumber = carNumber;
        this.announcement = announcement;
        this.accountInfo = accountInfo;
        this.phoneNumber = phoneNumber;
        this.createdDay = createdDay;
        this.foodtrucks = foodtrucks;
    }

    public void updateName(String name){
        this.name = name;
    }
    public void updatePicture(String picture){
        this.picture = picture;
    }
    public void updateCarNumber(String carNumber){
        this.carNumber = carNumber;
    }
    public void updateAnnouncement(String announcement){
        this.announcement = announcement;
    }
    public void updateAccountInfo(String accountInfo){
        this.accountInfo = accountInfo;
    }
    public void updatePhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
}
