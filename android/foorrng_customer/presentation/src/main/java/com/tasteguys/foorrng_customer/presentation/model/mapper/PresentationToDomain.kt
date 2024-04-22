package com.tasteguys.foorrng_customer.presentation.model.mapper

import com.tasteguys.foorrng_customer.domain.model.truck.TruckOperationData
import com.tasteguys.foorrng_customer.presentation.model.TruckOperationInfo

fun TruckOperationInfo.toDomain(): TruckOperationData{
    return TruckOperationData(
        day, startTime, endTime
    )
}
