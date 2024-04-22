package com.tasteguys.foorrng_customer.data.repository.food.remote

import com.tasteguys.foorrng_customer.data.model.food.FoodCategoryResponse
import com.tasteguys.foorrng_customer.data.model.user.FavoriteFoodRequest
import retrofit2.http.Body

interface FoodCategoryRemoteDataSource {
    suspend fun getCategory(): Result<FoodCategoryResponse>
    suspend fun selectFavoriteCategory(
        lat: Double, lng: Double, category:List<String>
    ): Result<Long?>

    suspend fun updateFavoriteCategory(
        lat: Double, lng: Double, category:List<String>
    ): Result<List<String>>
}