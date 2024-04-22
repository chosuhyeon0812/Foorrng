package com.tasteguys.foorrng_customer.data.model.truck

import com.squareup.moshi.Json

data class TruckFavoriteListResponse(
    @Json(name="foodtruckId")
    val id: Long,
    @Json(name="name")
    val name: String,
    @Json(name="img")
    val picture: String?,
    @Json(name="category")
    val category: List<String>,
    @Json(name="reviewCnt")
    val reviewCnt: Int
)