package com.tasteguys.foorrng_customer.presentation.model

data class TruckInfo(
    val truckId: Long,
    val name: String,
    val picture: String,
    val category: List<String>,
    val carNumber: String,
    val phoneNumber: String,
    val notice: String,
    val bussNumber: String,
    val menu: List<TruckMenu>,
    val review: TruckReview,
    val type: String = "Foodtruck"
)