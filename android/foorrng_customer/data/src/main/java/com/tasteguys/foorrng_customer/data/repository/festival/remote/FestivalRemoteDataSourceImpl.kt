package com.tasteguys.foorrng_customer.data.repository.festival.remote

import com.tasteguys.foorrng_customer.data.api.FestivalService
import com.tasteguys.foorrng_customer.data.mapper.toNonDefault
import com.tasteguys.foorrng_customer.data.model.festival.FestivalRegisterUpdateRequest
import com.tasteguys.foorrng_customer.data.model.festival.FestivalResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class FestivalRemoteDataSourceImpl @Inject constructor(
    private val festivalService: FestivalService
): FestivalRemoteDataSource {
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
        return if(mainImage!=null){
            val requestFile = mainImage.asRequestBody("image/*".toMediaTypeOrNull())
            festivalService.registerFestival(
                FestivalRegisterUpdateRequest(
                    -1, title, lat, lng, phoneNumber, email, kakao, organizer, startDate, endDate, address, content
                ), MultipartBody.Part.createFormData("mainImage", mainImage.name, requestFile),
            ).toNonDefault()
        }else{
            festivalService.registerFestival(
                FestivalRegisterUpdateRequest(
                    -1, title, lat, lng, phoneNumber, email, kakao, organizer, startDate, endDate, address, content
                ), null
            ).toNonDefault()
        }
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
        return if(mainImage!=null){
            val requestFile = mainImage.asRequestBody("image/*".toMediaTypeOrNull())
            festivalService.updateFestival(
                FestivalRegisterUpdateRequest(
                    id, title, lat, lng, phoneNumber, email, kakao, organizer, startDate, endDate, address, content
                ), MultipartBody.Part.createFormData("mainImage", mainImage.name, requestFile),
            ).toNonDefault()
        }else{
            festivalService.updateFestival(
                FestivalRegisterUpdateRequest(
                    id, title, lat, lng, phoneNumber, email, kakao, organizer, startDate, endDate, address, content
                ), null
            ).toNonDefault()
        }
    }

    override suspend fun getFestivalList(): Result<List<FestivalResponse>> {
        return festivalService.getFestivalList().toNonDefault()
    }

    override suspend fun getFestivalDetail(id: Long): Result<FestivalResponse> {
        return festivalService.getFestivalDetail(id).toNonDefault()
    }

    override suspend fun deleteFestival(id: Long): Result<String> {
        return festivalService.deleteFestival(id).toNonDefault()
    }
}