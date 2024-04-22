package com.tasteguys.foorrng_customer.data.model

import com.squareup.moshi.Json

data class LocationRequest(
    @Json(name="latitudeLeft")
    val latitudeLeft: Double,
    @Json(name="longitudeLeft")
    val longitudeLeft: Double,
    @Json(name="latitudeRight")
    val latitudeRight: Double,
    @Json(name="longitudeRight")
    val longitudeRight: Double,
)