package com.tasteguys.foorrng_customer.data.repository.festival.remote

import com.squareup.moshi.Json
import com.tasteguys.foorrng_customer.data.model.festival.FestivalResponse
import java.io.File

interface FestivalRemoteDataSource {
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

    suspend fun getFestivalList(): Result<List<FestivalResponse>>

    suspend fun getFestivalDetail(id: Long): Result<FestivalResponse>

    suspend fun deleteFestival(id: Long): Result<String>
}