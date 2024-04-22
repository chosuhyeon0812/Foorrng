package com.tasteguys.foorrng_owner.data.api

import com.tasteguys.foorrng_owner.data.model.DefaultResponse
import com.tasteguys.foorrng_owner.data.model.foodtruck.FoodtruckDetailResponse
import com.tasteguys.foorrng_owner.data.model.foodtruck.FoodtruckRegistRequest
import com.tasteguys.foorrng_owner.data.model.foodtruck.FoodtruckRegistResponse
import com.tasteguys.foorrng_owner.data.model.foodtruck.FoodtruckUpdateRequest
import com.tasteguys.foorrng_owner.data.model.mark.MarkRegistRequest
import com.tasteguys.foorrng_owner.data.model.mark.MarkRegistResponse
import com.tasteguys.foorrng_owner.data.model.mark.MarkResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface FoodtruckService {
    @Multipart
    @POST("foodtruck/regist")
    suspend fun registFoodtruck(
        @Part picture: MultipartBody.Part?,
        @Part("foodtruckDto") foodtruckCreateDto: FoodtruckRegistRequest
    ): Result<DefaultResponse<FoodtruckRegistResponse>>

    @GET("foodtrucks/owner")
    suspend fun getFoodtruck(): Result<DefaultResponse<FoodtruckDetailResponse>>

    @Multipart
    @PATCH("foodtruck/update")
    suspend fun updateFoodtruck(
        @Part picture: MultipartBody.Part?,
        @Part("foodtruckDto") foodtruckUpdateDto: FoodtruckUpdateRequest
    ): Result<DefaultResponse<FoodtruckRegistResponse>>

    @GET("mark/list")
    suspend fun getMarkList(): Result<DefaultResponse<List<MarkResponse>>>

    @POST("mark/{foodtruckId}/regist")
    suspend fun registMark(
        @Path("foodtruckId") foodtruckId: Long,
        @Body markDto: MarkRegistRequest
    ): Result<DefaultResponse<MarkRegistResponse>>

    @GET("mark/{markId}/change")
    suspend fun changeMarkRunState(
        @Path("markId") markId: Long
    ): Result<DefaultResponse<MarkResponse>>

    @DELETE("mark/{markId}")
    suspend fun deleteMark(
        @Path("markId") markId: Long
    ): Result<DefaultResponse<String>>
}