package com.tasteguys.foorrng_customer.data.model.truck

import com.squareup.moshi.Json

data class TruckListResponse(
    @Json(name = "foodtruckId")
    val truckId: Long,
    @Json(name = "markId")
    val markId: Long,
    @Json(name = "latitude")
    val latitude: Double,
    @Json(name = "longitutde")
    val longitude: Double,
    @Json(name = "name")
    val name: String,
    @Json(name = "img")
    val picture: String?,
    @Json(name = "type")
    val type: String,
    @Json(name= "category")
    val category: List<String>,
    @Json(name = "reviewCnt")
    val reviewCnt: Int,
    @Json(name = "like")
    val favorite: Boolean,
    @Json(name = "operation")
    val isOperating: Boolean
)
