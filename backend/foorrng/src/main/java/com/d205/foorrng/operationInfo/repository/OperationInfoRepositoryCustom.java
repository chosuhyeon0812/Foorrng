package com.d205.foorrng.operationInfo.repository;

import java.util.Set;

public interface OperationInfoRepositoryCustom {
    Set<String> findAllDaysByFoodTruckId(Long foodTruckId);
}
