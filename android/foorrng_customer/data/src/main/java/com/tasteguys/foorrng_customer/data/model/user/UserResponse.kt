package com.tasteguys.foorrng_customer.data.model.user

import com.squareup.moshi.Json

data class UserResponse(
    @Json(name="FavoriteFoodList")
    val category: List<String>,
    @Json(name="userUid")
    val uid: String
)