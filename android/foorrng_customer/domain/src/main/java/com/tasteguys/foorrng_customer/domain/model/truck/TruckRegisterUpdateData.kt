package com.tasteguys.foorrng_customer.domain.model.truck

data class TruckRegisterUpdateData(
    val id: Long,
    val announcement: String,
    val createdDay: Long,
    val picture: String?,
    val name: String,
    val accountInfo: String,
    val carNumber: String,
    val phoneNumber: String,
    val category: List<String>
)