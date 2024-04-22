package com.tasteguys.foorrng_customer.domain.model.truck

data class TruckData(
    val truckId: Long,
    val markId: Long,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val picture: String?,
    val type: String,
    val category: List<String>,
    val reviewCnt: Int,
    val favorite: Boolean,
    val isOperating: Boolean
)