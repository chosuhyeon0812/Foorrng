package com.tasteguys.foorrng_customer.data.model

data class DefaultResponseHeader(
    val successCode: Int,
    val resultCode: String,
    val resultMessage: String,
)