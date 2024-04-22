package com.d205.foorrng.foodtruck.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import java.io.Serializable;


@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodtruckId implements Serializable {
    private Long foodtrucksId;
    @Builder
    public FoodtruckId(Long foodtrucksId){
        this.foodtrucksId = foodtrucksId;
    }
}
