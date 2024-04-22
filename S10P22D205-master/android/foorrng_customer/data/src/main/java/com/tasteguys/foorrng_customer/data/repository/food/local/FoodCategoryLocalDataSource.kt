package com.tasteguys.foorrng_customer.data.repository.food.local

import com.tasteguys.foorrng_customer.data.model.FoodCategoryEntity

interface FoodCategoryLocalDataSource {

    suspend fun insertFoodCategoryData(name: String)

    suspend fun getFoodCategory(): List<FoodCategoryEntity>
}