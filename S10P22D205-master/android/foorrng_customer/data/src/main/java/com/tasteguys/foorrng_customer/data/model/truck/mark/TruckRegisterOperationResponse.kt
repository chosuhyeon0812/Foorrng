package com.tasteguys.foorrng_customer.data.model.truck.mark

import com.squareup.moshi.Json

data class TruckRegisterOperationResponse(
    @Json(name="markId")
    val id: Long,
    @Json(name="latitude")
    val lat: Double,
    @Json(name="longitude")
    val lng: Double,
    @Json(name="foodtruckId")
    val truckId: Long,
    @Json(name="operationInfoList")
    val operationInfo: List<TruckOperationInfo>
)
