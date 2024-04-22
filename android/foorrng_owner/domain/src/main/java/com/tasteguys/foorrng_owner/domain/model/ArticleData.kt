package com.tasteguys.foorrng_owner.domain.model

data class ArticleData(
    val articleId: Long,
    val userId: Long,
    val title: String,
    val latitude: Double,
    val longitude: Double,
    val phone: String,
    val email: String,
    val kakaoId: String,
    val organizer: String,
    val startDate: Long,
    val endDate: Long,
    val address: String,
    val mainImage: String
)
