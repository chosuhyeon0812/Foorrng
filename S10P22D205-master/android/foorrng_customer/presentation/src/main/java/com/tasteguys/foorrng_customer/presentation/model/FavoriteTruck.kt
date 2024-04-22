package com.tasteguys.foorrng_customer.presentation.model

data class FavoriteTruck(
    val id: Long,
    val name: String,
    val picture: String?,
    val category: List<String>,
    val reviewCnt: Int
)
