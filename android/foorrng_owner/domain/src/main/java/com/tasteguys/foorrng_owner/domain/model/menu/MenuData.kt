package com.tasteguys.foorrng_owner.domain.model.menu

data class MenuData(
    val id: Long,
    val name: String,
    val price: Long,
    val pictureUrl: String,
    val foodtruckId: Long,
)
