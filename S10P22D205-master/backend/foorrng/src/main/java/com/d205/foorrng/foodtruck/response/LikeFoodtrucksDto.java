package com.d205.foorrng.foodtruck.response;

import com.d205.foorrng.foodtruck.entity.Foodtrucks;
import com.d205.foorrng.user.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LikeFoodtrucksDto {
    private User user;
    private Foodtrucks foodtrucks;

    public static LikeFoodtrucksDto fromEntity(LikeFoodtrucksDto likeFoodtrucksDto){
        return new LikeFoodtrucksDto(
                likeFoodtrucksDto.getUser(),
                likeFoodtrucksDto.getFoodtrucks()
        );
    }
}
