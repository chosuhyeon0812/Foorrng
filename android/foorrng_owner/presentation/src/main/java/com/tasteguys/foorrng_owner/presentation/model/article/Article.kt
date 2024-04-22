package com.tasteguys.foorrng_owner.presentation.model.article

import com.naver.maps.geometry.LatLng

data class Article(
    val articleId: Long,
    val userId: Long,
    val title: String,
    val latLng: LatLng,
    val phone: String,
    val email: String,
    val kakaoId: String,
    val organizer: String,
    val startDate: Long,
    val endDate: Long,
    val address: String,
    val mainImage: String
)
