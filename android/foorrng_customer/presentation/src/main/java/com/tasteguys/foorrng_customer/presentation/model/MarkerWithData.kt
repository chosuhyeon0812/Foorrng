package com.tasteguys.foorrng_customer.presentation.model

import com.naver.maps.map.overlay.Marker

data class MarkerWithData(
    val marker: Marker,
    val data: TruckDataWithAddress
)
