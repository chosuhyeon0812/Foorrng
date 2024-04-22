package com.tasteguys.foorrng_customer.domain.usecase.festival

import com.tasteguys.foorrng_customer.domain.repository.FestivalRepository
import java.io.File
import javax.inject.Inject

class FestivalRegisterUpdateUseCase @Inject constructor(
    private val festivalRepository: FestivalRepository
) {
    suspend operator fun invoke(
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
    ) = festivalRepository.registerFestival(
        title,
        lat,
        lng,
        phoneNumber,
        email,
        kakao,
        organizer,
        startDate,
        endDate,
        address,
        mainImage,
        content
    )

    suspend operator fun invoke(
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
    ) = festivalRepository.updateFestival(
        id,
        title,
        lat,
        lng,
        phoneNumber,
        email,
        kakao,
        organizer,
        startDate,
        endDate,
        address,
        mainImage,
        content
    )
}