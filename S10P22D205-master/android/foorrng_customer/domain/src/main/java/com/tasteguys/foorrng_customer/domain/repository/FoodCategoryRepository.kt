package com.tasteguys.foorrng_customer.domain.repository

import com.tasteguys.foorrng_customer.domain.model.food.FoodCategoryData

interface FoodCategoryRepository {
    suspend fun insertFoodCategory(name: String)
    suspend fun getFoodCategory(): List<FoodCategoryData>

    suspend fun getCategory(): Result<List<String>>
    suspend fun selectFavoriteCategory(
        lat: Double, lng: Double, category: List<String>
    ): Result<Long?>

    suspend fun updateFavoriteCategory(
        lat: Double, lng: Double, category: List<String>
    ): Result<List<String>>

}