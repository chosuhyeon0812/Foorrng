package com.tasteguys.foorrng_owner.domain.model.foodtruck

data class FoodtruckInfoData(
    val foodtruckId: Long,
    val announcement: String,
    val createdDay: Long,
    val name: String,
    val accountInfo: String,
    val carNumber: String,
    val phoneNumber: String,
    val category: List<String>,
    val picture: String,
    val businessNumber: String,
    val totalReview: Int,
    val reviews: List<ReviewData>
)