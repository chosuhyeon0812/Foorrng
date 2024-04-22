package com.tasteguys.foorrng_owner.domain.repository

import com.tasteguys.foorrng_owner.domain.model.foodtruck.FoodtruckInfoData
import com.tasteguys.foorrng_owner.domain.model.mark.MarkData
import java.io.File

interface FoodtruckRepository {
    suspend fun registFoodtruck(
        name: String,
        carNumber: String,
        accountInfo: String,
        phoneNumber: String,
        announcement: String,
        category: List<String>,
        picture: File?
    ) : Result<Boolean>

    suspend fun getFoodtruck() : Result<FoodtruckInfoData>

    suspend fun updateFoodtruck(
        foodtruckId: Long,
        name: String,
        carNumber: String,
        accountInfo: String,
        phoneNumber: String,
        announcement: String,
        category: List<String>,
        picture: File?
    ) : Result<Boolean>

    suspend fun getMarkList(): Result<List<MarkData>>

    suspend fun registMark(
        foodtruckId: Long,
        markData: MarkData
    ): Result<Boolean>

    suspend fun changeMarkRunState(
        markId: Long
    ): Result<Boolean>

    suspend fun deleteMark(
        markId: Long
    ): Result<String>
}