package com.tasteguys.foorrng_customer.data.model.user

import com.squareup.moshi.Json

data class LoginResponse(
    @Json(name="accessToken")
    val accessToken: String,
//    val refreshToken: String
    @Json(name="serveyCheck")
    val surveyChecked: Boolean

)
