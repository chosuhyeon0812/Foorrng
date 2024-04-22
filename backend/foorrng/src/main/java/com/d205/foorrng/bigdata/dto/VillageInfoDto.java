package com.d205.foorrng.bigdata.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class VillageInfoDto {
    private String village;
    private List<BoundaryDto> position;
}
