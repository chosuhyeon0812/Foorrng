package com.tasteguys.foorrng_customer.data.model

import androidx.room.Entity

@Entity(tableName = "food_category_table", primaryKeys = ["name"])
data class FoodCategoryEntity(
    val name: String
)
