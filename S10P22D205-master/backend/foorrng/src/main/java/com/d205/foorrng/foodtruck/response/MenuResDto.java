package com.d205.foorrng.foodtruck.response;

import com.d205.foorrng.foodtruck.entity.Menu;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MenuResDto {
    private Long id;
    private String name;
    private Long price;
    private String picture;
    private Long foodtruck;

    public static MenuResDto fromEntity (Menu menu){
        return new MenuResDto(
                menu.getId(),
                menu.getName(),
                menu.getPrice(),
                menu.getPicture(),
                menu.getFoodtrucks().getId()
        );
    }
}


