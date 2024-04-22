package com.d205.foorrng.foodtruck.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodtruckReportId implements Serializable {
    private Long foodtrucksId;
    public FoodtruckReportId(Long foodtrucksId){
        this.foodtrucksId = foodtrucksId;
    }
}
