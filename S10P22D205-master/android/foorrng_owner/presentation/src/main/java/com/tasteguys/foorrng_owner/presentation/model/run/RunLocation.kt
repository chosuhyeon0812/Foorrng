package com.tasteguys.foorrng_owner.presentation.model.run

import com.naver.maps.geometry.LatLng

data class RunLocation(
    val address: String,
    val latLng: LatLng
)