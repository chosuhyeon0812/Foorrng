package com.d205.foorrng.foodtruck.response;

import lombok.*;
import java.util.List;


@Getter
public class FoodtrucksResDto {
    private Long foodtruckId;
    private Long markId;
    private Double latitude;
    private Double longitutde;
    private String name;
    private String img;
    private String type;
    private List<String> category;
    private Integer reviewCnt;
    private Boolean like;
    private Boolean operation;

    public FoodtrucksResDto(Long foodtruckId, Long markId, Double latitude, Double longitutde, String name, String img, String type, List<String> category, Integer reviewCnt, Boolean like, Boolean operation){
        this.foodtruckId = foodtruckId;
        this.markId = markId;
        this.latitude = latitude;
        this.longitutde = longitutde;
        this.name = name;
        this.img = img;
        this.type = type;
        this.category = category;
        this.reviewCnt = reviewCnt;
        this.like = like;
        this.operation = operation;

    }
}

