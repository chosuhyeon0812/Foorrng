package com.tasteguys.foorrng_owner.domain.model.mark

data class MarkData(
    val id: Long,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val isOpen: Boolean,
    val operationInfoList: List<OperationInfoData>
)