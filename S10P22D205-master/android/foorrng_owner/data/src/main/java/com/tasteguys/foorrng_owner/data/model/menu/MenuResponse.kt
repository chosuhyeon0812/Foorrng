package com.tasteguys.foorrng_owner.data.model.menu

import com.squareup.moshi.Json

data class MenuResponse(
    val id: Long,
    val name: String,
    val price: Long,
    @Json(name = "picture")
    val pictureUrl: String,
    @Json(name = "foodtruck")
    val foodtruckId: Long,
)
