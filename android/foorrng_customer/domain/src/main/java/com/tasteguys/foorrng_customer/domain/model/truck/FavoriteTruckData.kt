package com.tasteguys.foorrng_customer.domain.model.truck

data class FavoriteTruckData(
    val id: Long,
    val name: String,
    val picture: String?,
    val category: List<String>,
    val reviewCnt: Int
)
