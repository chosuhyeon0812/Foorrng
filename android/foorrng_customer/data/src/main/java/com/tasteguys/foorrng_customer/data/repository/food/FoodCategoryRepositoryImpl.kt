package com.tasteguys.foorrng_customer.data.repository.food

import com.tasteguys.foorrng_customer.data.mapper.toDomain
import com.tasteguys.foorrng_customer.data.repository.food.local.FoodCategoryLocalDataSource
import com.tasteguys.foorrng_customer.data.repository.food.remote.FoodCategoryRemoteDataSource
import com.tasteguys.foorrng_customer.domain.model.food.FoodCategoryData
import com.tasteguys.foorrng_customer.domain.repository.FoodCategoryRepository
import javax.inject.Inject

class FoodCategoryRepositoryImpl @Inject constructor(
    private val foodCategoryLocalDataSource: FoodCategoryLocalDataSource,
    private val foodCategoryRemoteDataSource: FoodCategoryRemoteDataSource
): FoodCategoryRepository {
    override suspend fun insertFoodCategory(name: String) {
        foodCategoryLocalDataSource.insertFoodCategoryData(name)
    }

    override suspend fun getFoodCategory(): List<FoodCategoryData> {
        return foodCategoryLocalDataSource.getFoodCategory().map{
            it.toDomain()
        }
    }

    override suspend fun getCategory(): Result<List<String>> {
        return foodCategoryRemoteDataSource.getCategory().map { it.category }
    }

    override suspend fun selectFavoriteCategory(
        lat: Double,
        lng: Double,
        category: List<String>
    ): Result<Long?> {
        return foodCategoryRemoteDataSource.selectFavoriteCategory(lat, lng, category)
    }

    override suspend fun updateFavoriteCategory(
        lat: Double,
        lng: Double,
        category: List<String>
    ): Result<List<String>> {
        return foodCategoryRemoteDataSource.updateFavoriteCategory(lat, lng, category)
    }


}