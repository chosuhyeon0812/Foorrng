package com.tasteguys.foorrng_customer.domain.repository

import com.tasteguys.foorrng_customer.domain.model.festival.FestivalData
import java.io.File

interface FestivalRepository {

    suspend fun registerFestival(
        title: String,
        lat: Double,
        lng: Double,
        phoneNumber: String,
        email: String,
        kakao: String,
        organizer: String,
        startDate: Long,
        endDate: Long,
        address: String,
        mainImage: File?,
        content: String?
    ): Result<String>

    suspend fun updateFestival(
        id: Long,
        title: String,
        lat: Double,
        lng: Double,
        phoneNumber: String,
        email: String,
        kakao: String,
        organizer: String,
        startDate: Long,
        endDate: Long,
        address: String,
        mainImage: File?,
        content: String?
    ): Result<String>

    suspend fun getFestivalList(): Result<List<FestivalData>>

    suspend fun getFestivalDetail(id: Long): Result<FestivalData>

    suspend fun deleteFestival(id: Long): Result<String>
}