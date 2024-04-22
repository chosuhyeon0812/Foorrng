package com.d205.foorrng.foodtruck.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Entity
@Getter
@Setter
@Validated
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_seq")
    private Long id;

    private String name;        // 메뉴 이름

    private Long price;         // 메뉴 가격

    private String picture;     // 메뉴 사진

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="foodtrucks_seq")
    private Foodtrucks foodtrucks;


    @Builder
    public Menu(String name, Long price, String picture, Foodtrucks foodtrucks){
        this.name = name;
        this.price = price;
        this.picture = picture;
        this.foodtrucks = foodtrucks;
    }

    public void changeName(String name){
        this.name = name;
    }

    public void changePrice(Long price){
        this.price = price;
    }

    public void changePicture(String imgUrl){
        this.picture = imgUrl;
    }

}
