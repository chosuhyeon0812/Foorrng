package com.tasteguys.foorrng_customer.domain.model.truck

data class TruckDetailMarkData(
    val id: Long,
    val lat: Double,
    val lng: Double,
    val address: String,
    val isOpen: Boolean,
    val operationInfoList: List<TruckOperationData>
)
