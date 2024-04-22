package com.tasteguys.foorrng_customer.data.repository.food.local

import com.tasteguys.foorrng_customer.data.dao.FoodCategoryDao
import com.tasteguys.foorrng_customer.data.model.FoodCategoryEntity
import javax.inject.Inject

class FoodCategoryLocalDataSourceImpl @Inject constructor(
    private val foodCategoryDao: FoodCategoryDao
) : FoodCategoryLocalDataSource {
    override suspend fun insertFoodCategoryData(name: String) {
        foodCategoryDao.insertFoodCategory(
            FoodCategoryEntity(name)
        )
    }

    override suspend fun getFoodCategory(): List<FoodCategoryEntity> {
        return foodCategoryDao.getFoodCategory()
    }
}