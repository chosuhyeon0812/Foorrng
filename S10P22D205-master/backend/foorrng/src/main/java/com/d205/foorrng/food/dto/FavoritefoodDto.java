package com.d205.foorrng.food.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class FavoritefoodDto {

    private Double latitude;

    private Double longitude;

    private List<String> favoriteFoods;

}
