package com.tasteguys.foorrng_customer.data.model.food

import com.squareup.moshi.Json

data class FoodCategoryResponse(
    @Json(name="favoritefoodList")
    val category: List<String>
)
