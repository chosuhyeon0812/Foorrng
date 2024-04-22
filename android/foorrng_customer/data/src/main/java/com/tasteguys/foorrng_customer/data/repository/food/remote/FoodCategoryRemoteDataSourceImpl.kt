package com.tasteguys.foorrng_customer.data.repository.food.remote

import com.tasteguys.foorrng_customer.data.api.FoodService
import com.tasteguys.foorrng_customer.data.mapper.toNonDefault
import com.tasteguys.foorrng_customer.data.model.food.FoodCategoryResponse
import com.tasteguys.foorrng_customer.data.model.user.FavoriteFoodRequest
import javax.inject.Inject

class FoodCategoryRemoteDataSourceImpl @Inject constructor(
    private val foodService:FoodService
): FoodCategoryRemoteDataSource {
    override suspend fun getCategory(): Result<FoodCategoryResponse> {
        return foodService.getCategory().toNonDefault()
    }

    override suspend fun selectFavoriteCategory(
        lat: Double,
        lng: Double,
        category: List<String>
    ): Result<Long?> {
        return foodService.selectFavoriteCategory(
            FavoriteFoodRequest(lat, lng, category)
        ).toNonDefault()
    }

    override suspend fun updateFavoriteCategory(
        lat: Double,
        lng: Double,
        category: List<String>
    ): Result<List<String>> {
        return foodService.updateFavoriteCategory(
            FavoriteFoodRequest(lat, lng, category)
        ).toNonDefault()
    }
}