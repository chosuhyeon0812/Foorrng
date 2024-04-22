package com.tasteguys.foorrng_customer.data.model.truck.menu

import com.squareup.moshi.Json

data class TruckMenuRequest(
    val name: String,
    val price: Long,
    @Json(name="foodtruck")
    val truckId: Long
)
