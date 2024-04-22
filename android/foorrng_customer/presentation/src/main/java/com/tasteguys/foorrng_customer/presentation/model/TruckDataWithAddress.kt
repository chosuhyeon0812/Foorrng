package com.tasteguys.foorrng_customer.presentation.model

data class TruckDataWithAddress(
    val truckId: Long,
    val markId: Long,
    val name: String,
    val picture: String?,
    val isFavorite: Boolean,
    val numOfReview: Int,
    val distance: Int,
    val category: List<String>,
    val type: String = "Foodtruck",
    val isOperating: Boolean,
    val address: String = "",
    val lat: Double,
    val lng: Double
)
