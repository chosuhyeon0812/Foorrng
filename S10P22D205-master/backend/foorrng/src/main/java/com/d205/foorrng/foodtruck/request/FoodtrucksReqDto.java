package com.d205.foorrng.foodtruck.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class FoodtrucksReqDto {
    @NotNull
    private double latitudeLeft;
    @NotNull
    private double longitudeLeft;
    @NotNull
    private double latitudeRight;
    @NotNull
    private double longitudeRight;
}
