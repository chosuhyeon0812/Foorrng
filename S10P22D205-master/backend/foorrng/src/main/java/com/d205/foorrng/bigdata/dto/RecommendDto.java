package com.d205.foorrng.bigdata.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RecommendDto {
    private String food;
    private List<VillageInfoDto> recommend;
}
