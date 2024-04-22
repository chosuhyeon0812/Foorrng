package com.tasteguys.foorrng_owner.data.model.foodtruck

import java.io.Serializable

data class FoodtruckRegistRequest(
    val name: String,
    val carNumber: String,
    val accountInfo: String,
    val phoneNumber: String,
    val announcement: String,
    val category: List<String>,
) : Serializable
