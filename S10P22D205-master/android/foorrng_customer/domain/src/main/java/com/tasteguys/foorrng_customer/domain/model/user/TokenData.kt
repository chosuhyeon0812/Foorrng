package com.tasteguys.foorrng_customer.domain.model.user

data class TokenData(
    val accessToken: String,
//    val refreshToken: String
    val surveyChecked: Boolean
)