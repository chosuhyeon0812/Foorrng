package com.tasteguys.foorrng_owner.data.model.recommend

import com.squareup.moshi.Json

data class RecommendResponse(
    val food: String,
    @Json(name = "recommend")
    val recommendList: List<VillageResponse>
)
