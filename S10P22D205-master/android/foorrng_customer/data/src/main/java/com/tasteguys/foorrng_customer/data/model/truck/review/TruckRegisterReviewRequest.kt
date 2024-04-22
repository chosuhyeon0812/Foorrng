package com.tasteguys.foorrng_customer.data.model.truck.review

import com.squareup.moshi.Json

data class TruckRegisterReviewRequest(
    val rvIsDelicious: Boolean,
    val rvIsCool: Boolean,
    val rvIsClean: Boolean,
    val rvIsKind: Boolean,
    val rvIsSpecial: Boolean,
    val rvIsCheap: Boolean,
    val rvIsFast: Boolean
)
