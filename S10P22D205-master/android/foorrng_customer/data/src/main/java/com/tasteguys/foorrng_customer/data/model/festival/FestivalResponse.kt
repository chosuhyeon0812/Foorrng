package com.tasteguys.foorrng_customer.data.model.festival

import com.squareup.moshi.Json

data class FestivalResponse(
    @Json(name="articleId")
    val id: Long,
    @Json(name="title")
    val title: String,
    @Json(name="latitude")
    val lat: Double,
    @Json(name="longitude")
    val lng: Double,
    @Json(name="phone")
    val phoneNumber: String,
    @Json(name="email")
    val email: String,
    @Json(name="kakaoId")
    val kakao: String,
    @Json(name="organizer")
    val organizer: String,
    @Json(name="startDate")
    val startDate: Long,
    @Json(name="endDate")
    val endDate: Long,
    @Json(name="address")
    val address: String,
    @Json(name="mainImage")
    val picture: String?,
    @Json(name="content")
    val content: String?

)
