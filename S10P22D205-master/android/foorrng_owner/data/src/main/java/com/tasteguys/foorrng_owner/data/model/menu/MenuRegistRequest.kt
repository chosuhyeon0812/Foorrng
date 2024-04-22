package com.tasteguys.foorrng_owner.data.model.menu

import com.squareup.moshi.Json

data class MenuRegistRequest(
    val name: String,
    val price: Long,
    @Json(name = "foodtruck")
    val foodtruckId: Long,
)
