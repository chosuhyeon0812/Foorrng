package com.tasteguys.foorrng_customer.domain.model.festival

data class FestivalData(
    val id: Long,
    val title: String,
    val lat: Double,
    val lng: Double,
    val phoneNumber: String,
    val email: String,
    val kakao: String,
    val organizer: String,
    val startDate: Long,
    val endDate: Long,
    val address: String,
    val picture: String?,
    val content: String?
)
