package com.tasteguys.foorrng_customer.data.model.truck.menu

import com.squareup.moshi.Json

data class TruckMenuResponse(
    val id: Long,
    val name: String,
    val price: Long,
    val picture: String,
    @Json(name="foodtruck")
    val truckId: Long
)