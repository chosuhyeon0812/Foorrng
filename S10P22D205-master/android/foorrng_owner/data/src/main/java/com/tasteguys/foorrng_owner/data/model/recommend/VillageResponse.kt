package com.tasteguys.foorrng_owner.data.model.recommend

import com.squareup.moshi.Json

data class VillageResponse(
    @Json(name = "village")
    val villageName: String,
    @Json(name = "position")
    val area: List<CoodinateResponse>
)
