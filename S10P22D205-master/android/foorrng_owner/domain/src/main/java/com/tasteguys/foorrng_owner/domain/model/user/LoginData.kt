package com.tasteguys.foorrng_owner.domain.model.user

data class LoginData(
    val isBusiRegist: Boolean,
    val accessToken: String,
    val refreshToken: String
)
