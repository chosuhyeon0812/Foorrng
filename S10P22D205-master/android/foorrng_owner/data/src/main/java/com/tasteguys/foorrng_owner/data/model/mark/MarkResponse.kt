package com.tasteguys.foorrng_owner.data.model.mark

data class MarkResponse(
    val id: Long,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val isOpen: Boolean,
    val operationInfoList: List<OperationInfoResponse>
)