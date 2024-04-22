package com.tasteguys.foorrng_customer.presentation.model

data class TruckMark(
    val markId: Long,
    val truckId: Long,
    val latitude: Int,
    val longitude: Int,
    val address: String,
    val operationInfo: List<TruckOperationInfo>,
    val type: String = "Foodtruck"

)
