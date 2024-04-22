package com.tasteguys.foorrng_customer.data.model.truck

import com.squareup.moshi.Json

data class TruckMainInfoResponse(
    @Json(name="foodtruckId")
    val truckId: Long,
    val announcement: String,
    val name: String,
    val createdDay: Long,
    val picture: String?,
    val accountInfo: String,
    val carNumber: String,
    val phoneNumber: String,

    @Json(name="businessNumber")
    val bussiNumber: String?,
    val category: List<String>

)