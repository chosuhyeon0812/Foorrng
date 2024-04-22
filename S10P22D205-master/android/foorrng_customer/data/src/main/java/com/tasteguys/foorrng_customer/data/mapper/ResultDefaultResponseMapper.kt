package com.tasteguys.foorrng_customer.data.mapper

import com.tasteguys.foorrng_customer.data.model.DefaultResponse

fun <T> Result<DefaultResponse<T>>.toNonDefault(): Result<T> {
    return this.map {
        it.data
    }
}