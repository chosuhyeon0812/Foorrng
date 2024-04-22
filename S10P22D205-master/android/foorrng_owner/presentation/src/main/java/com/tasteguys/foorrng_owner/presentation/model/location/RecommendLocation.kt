package com.tasteguys.foorrng_owner.presentation.model.location

import com.naver.maps.geometry.LatLng

data class RecommendLocation(
    val address: String,
    val foodList: List<String>,
    val area: List<LatLng>
)