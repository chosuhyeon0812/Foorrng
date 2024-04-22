package com.tasteguys.foorrng_customer.data.model.user

import com.squareup.moshi.Json

data class FavoriteFoodRequest(
    @Json(name= "latitude")
    val lat: Double,
    @Json(name="longitude")
    val lng: Double,
    @Json(name="favoriteFoods")
    val category: List<String>
)
