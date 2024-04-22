package com.tasteguys.foorrng_owner.presentation.model.foodtruck

data class FoodTruckInfo(
    val id: Long,
    val name: String,
    val carNumber: String,
    val callNumber: String,
    val accountInfo: String,
    val category: List<String>,
    val notice: String,
    val pictureUrl: String,
    val businessNumber: String,
    val reviewSet: ReviewSet,
)
