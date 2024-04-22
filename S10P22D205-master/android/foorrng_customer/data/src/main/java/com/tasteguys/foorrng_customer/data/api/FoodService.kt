package com.tasteguys.foorrng_customer.data.api

import com.tasteguys.foorrng_customer.data.model.DefaultResponse
import com.tasteguys.foorrng_customer.data.model.food.FoodCategoryResponse
import com.tasteguys.foorrng_customer.data.model.user.FavoriteFoodRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface FoodService {
    @GET("food/favorite")
    suspend fun getCategory(): Result<DefaultResponse<FoodCategoryResponse>>

    @POST("food/favorite")
    suspend fun selectFavoriteCategory(
        @Body foodRequest: FavoriteFoodRequest
    ): Result<DefaultResponse<Long?>>

    @PATCH("food/favorite")
    suspend fun updateFavoriteCategory(
        @Body foodRequest: FavoriteFoodRequest
    ): Result<DefaultResponse<List<String>>>
}