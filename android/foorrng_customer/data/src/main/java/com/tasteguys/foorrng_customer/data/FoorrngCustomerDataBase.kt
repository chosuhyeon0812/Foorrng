package com.tasteguys.foorrng_customer.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tasteguys.foorrng_customer.data.dao.FoodCategoryDao
import com.tasteguys.foorrng_customer.data.model.FoodCategoryEntity

@Database(
    entities = [FoodCategoryEntity::class],
    version = 1
)
abstract class FoorrngCustomerDataBase: RoomDatabase() {
    abstract fun foodCategoryDao(): FoodCategoryDao
}