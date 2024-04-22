package com.d205.foorrng.foodtruck.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Entity
@Getter
@Validated
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Foodtruck {
    @EmbeddedId
    private FoodtruckId foodtruckId;
    private String announcement;
    private Long createdDay;
    private String picture;
    private String name;
    private String accountInfo;
    private String carNumber;
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "foodtruck_id", referencedColumnName = "foodtrucks_seq")
    private Foodtrucks foodtrucks;

    @Builder
    public Foodtruck(FoodtruckId foodtruckId,String announcement, Long createdDay, String picture, String name, String accountInfo, String carNumber, String phoneNumber, Foodtrucks foodtrucks){
        this.foodtruckId = foodtruckId;
        this.announcement = announcement;
        this.createdDay = createdDay;
        this.picture = picture;
        this.name = name;
        this.accountInfo = accountInfo;
        this.carNumber = carNumber;
        this.phoneNumber = phoneNumber;
        this.foodtrucks = foodtrucks;
    }

    public void updateAnnouncement(String announcement) {
        this.announcement = announcement;
    }
    public void updatePicture(String picture) {
        this.picture = picture;
    }
    public void updateName(String name) {
        this.name = name;
    }
    public void updateAccountInfo(String accountInfo) {
        this.accountInfo = accountInfo;
    }
    public void updateCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }
    public void updatePhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public FoodtruckId getId() {
        return foodtruckId;
    }
}
