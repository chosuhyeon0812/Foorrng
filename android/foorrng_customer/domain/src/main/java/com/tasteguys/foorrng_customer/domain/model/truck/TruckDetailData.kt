package com.tasteguys.foorrng_customer.domain.model.truck

data class TruckDetailData(
    val type: String,
    val mainData: TruckMainData,
    val reviews: List<TruckReviewData>,
    val menus: List<TruckMenuData>,
    val totalReview: Int,
    val operation: List<TruckDetailMarkData>
)