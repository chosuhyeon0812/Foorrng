package com.tasteguys.foorrng_owner.data.model.foodtruck

data class FoodtruckDetailResponse(
    val foodtruck: FoodtruckRegistResponse,
    val review: List<ReviewResponse>,
    val totalReview: Int
)