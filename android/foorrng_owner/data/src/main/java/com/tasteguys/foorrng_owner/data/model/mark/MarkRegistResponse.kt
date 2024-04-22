package com.tasteguys.foorrng_owner.data.model.mark

import com.squareup.moshi.Json

data class MarkRegistResponse(
    @Json(name = "markId")
    val id: Long,
    val latitude: Double,
    val longitude: Double,
    val operationInfoList: List<OperationInfoResponse>,
    val foodtruckId: Long
)