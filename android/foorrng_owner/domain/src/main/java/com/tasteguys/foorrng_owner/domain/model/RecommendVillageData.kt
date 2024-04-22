package com.tasteguys.foorrng_owner.domain.model

data class RecommendVillageData(
    val address: String,
    val foodList: List<String>,
    val area: List<CoordinateData>
)
