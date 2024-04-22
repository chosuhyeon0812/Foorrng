package com.d205.foorrng.food.repository;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FavoritefoodList {

    RICE_BOWL("덮밥"),
    ROAST_CHICKEN("전기구이통닭"),
    SKEWERS("꼬치"),
    TAKOYAKI("타코야끼"),
    TACO_KEBAB("타코 & 케밥"),
    KOREAN_STREETFOOD("분식"),
    BREAD("빵"),
    FAMINE_CROPS("구황작물"),
    CAFE_DESSERT("카페 & 디저트"),
    ETC("기타");

    private final String menu;

}
