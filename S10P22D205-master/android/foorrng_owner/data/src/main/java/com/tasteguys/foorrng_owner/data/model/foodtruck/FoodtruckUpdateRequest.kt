package com.tasteguys.foorrng_owner.data.model.foodtruck

data class FoodtruckUpdateRequest(
    val foodtruckId: Long,
    val announcement: String,
    val name: String,
    val accountInfo: String,
    val carNumber: String,
    val phoneNumber: String,
    val category: List<String>
)