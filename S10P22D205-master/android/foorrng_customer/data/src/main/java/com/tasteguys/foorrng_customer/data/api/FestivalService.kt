package com.tasteguys.foorrng_customer.data.api

import com.tasteguys.foorrng_customer.data.model.DefaultResponse
import com.tasteguys.foorrng_customer.data.model.festival.FestivalRegisterUpdateRequest
import com.tasteguys.foorrng_customer.data.model.festival.FestivalResponse
import okhttp3.MultipartBody
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface FestivalService {
    @Multipart
    @POST("article/regist")
    suspend fun registerFestival(
        @Part("articleReqDto") festivalRequest: FestivalRegisterUpdateRequest,
        @Part mainImage: MultipartBody.Part?
    ): Result<DefaultResponse<String>>

    @Multipart
    @PATCH("article/update")
    suspend fun updateFestival(
        @Part("articleDto") festivalRequest: FestivalRegisterUpdateRequest,
        @Part mainImage: MultipartBody.Part?
    ): Result<DefaultResponse<String>>

    @GET("article/my/list/")
    suspend fun getFestivalList(): Result<DefaultResponse<List<FestivalResponse>>>

    @GET("article/search/{article-id}")
    suspend fun getFestivalDetail(@Path("article-id") id: Long): Result<DefaultResponse<FestivalResponse>>

    @DELETE("article/delete/{article-id}")
    suspend fun deleteFestival(@Path("article-id") id: Long):Result<DefaultResponse<String>>


}