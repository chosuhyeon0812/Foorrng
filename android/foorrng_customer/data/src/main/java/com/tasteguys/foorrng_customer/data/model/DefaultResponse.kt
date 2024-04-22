package com.tasteguys.foorrng_customer.data.model

import com.squareup.moshi.Json

class DefaultResponse<T>(
    @Json(name = "dataHeader")
    val header: DefaultResponseHeader,
    @Json(name = "dataBody")
    val data: T
)