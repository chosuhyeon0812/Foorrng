package com.tasteguys.foorrng_customer.data.model.truck

import com.squareup.moshi.Json
import com.tasteguys.foorrng_customer.data.model.truck.mark.TruckOperationInfo

data class TruckDetailMarkResponse(
    val id: Long,
    @Json(name="latitude")
    val lat: Double,
    @Json(name="longitude")
    val lng: Double,
    val address: String,
    val isOpen: Boolean,
    val operationInfoList: List<TruckOperationInfo>
)
