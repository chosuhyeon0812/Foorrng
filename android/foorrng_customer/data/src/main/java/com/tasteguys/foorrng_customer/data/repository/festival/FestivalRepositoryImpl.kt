package com.tasteguys.foorrng_customer.data.repository.festival

import com.tasteguys.foorrng_customer.data.mapper.toDomain
import com.tasteguys.foorrng_customer.data.repository.festival.remote.FestivalRemoteDataSource
import com.tasteguys.foorrng_customer.domain.model.festival.FestivalData
import com.tasteguys.foorrng_customer.domain.repository.FestivalRepository
import java.io.File
import javax.inject.Inject

class FestivalRepositoryImpl @Inject constructor(
    private val festivalRemoteDataSource: FestivalRemoteDataSource
) : FestivalRepository {
    override suspend fun registerFestival(
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
    ): Result<String> {
        return festivalRemoteDataSource.registerFestival(
            title, lat, lng, phoneNumber, email, kakao, organizer, startDate, endDate, address, mainImage, content
        )
    }

    override suspend fun updateFestival(
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
    ): Result<String> {
        return festivalRemoteDataSource.updateFestival(
            id, title, lat, lng, phoneNumber, email, kakao, organizer, startDate, endDate, address, mainImage, content
        )
    }

    override suspend fun getFestivalList(): Result<List<FestivalData>> {
        return festivalRemoteDataSource.getFestivalList().map {
            lst -> lst.map { it.toDomain() }
        }
    }

    override suspend fun getFestivalDetail(id: Long): Result<FestivalData> {
        return festivalRemoteDataSource.getFestivalDetail(id).map { it.toDomain() }
    }

    override suspend fun deleteFestival(id: Long): Result<String> {
        return festivalRemoteDataSource.deleteFestival(id)
    }
}