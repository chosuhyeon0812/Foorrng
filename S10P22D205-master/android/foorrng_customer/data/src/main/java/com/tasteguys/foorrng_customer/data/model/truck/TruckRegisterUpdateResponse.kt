package com.tasteguys.foorrng_customer.data.model.truck

import com.squareup.moshi.Json

data class TruckRegisterUpdateResponse(
    @Json(name = "foodtruckId")
    val id: Long,
    val announcement: String,
    val createdDay: Long,
    val picture: String?,
    val name: String,
    val accountInfo: String,
    val carNumber: String,
    val phoneNumber: String,
    val category: List<String>
)
