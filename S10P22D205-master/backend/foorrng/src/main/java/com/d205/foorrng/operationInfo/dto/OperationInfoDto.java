package com.d205.foorrng.operationInfo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter @Setter
public class OperationInfoDto {

    private List<Map<String, Object>> operationInfoList = new ArrayList<>();


}
