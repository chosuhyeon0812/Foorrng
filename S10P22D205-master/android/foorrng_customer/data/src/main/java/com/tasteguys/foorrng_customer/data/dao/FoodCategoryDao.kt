package com.tasteguys.foorrng_customer.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tasteguys.foorrng_customer.data.model.FoodCategoryEntity

@Dao
interface FoodCategoryDao {
    @Insert
    suspend fun insertFoodCategory(foodCategory:FoodCategoryEntity)

    @Query("select * from food_category_table")
    suspend fun getFoodCategory(): List<FoodCategoryEntity>

}