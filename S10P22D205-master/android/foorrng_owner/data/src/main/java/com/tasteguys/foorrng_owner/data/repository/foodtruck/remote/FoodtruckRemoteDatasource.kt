package com.tasteguys.foorrng_owner.data.repository.foodtruck.remote

import com.tasteguys.foorrng_owner.data.model.DefaultResponse
import com.tasteguys.foorrng_owner.data.model.foodtruck.FoodtruckDetailResponse
import com.tasteguys.foorrng_owner.data.model.mark.MarkRegistRequest
import com.tasteguys.foorrng_owner.data.model.mark.MarkResponse
import retrofit2.http.DELETE
import retrofit2.http.Path
import java.io.File

interface FoodtruckRemoteDatasource {
    suspend fun registFoodtruck(
        name: String,
        carNumber: String,
        accountInfo: String,
        phoneNumber: String,
        announcement: String,
        category: List<String>,
        picture: File?
    ): Result<Boolean>

    suspend fun getFoodtruck(): Result<FoodtruckDetailResponse>

    suspend fun updateFoodtruck(
        foodtruckId: Long,
        name: String,
        carNumber: String,
        accountInfo: String,
        phoneNumber: String,
        announcement: String,
        category: List<String>,
        picture: File?
    ): Result<Boolean>

    suspend fun getMarkList(): Result<List<MarkResponse>>

    suspend fun registMark(
        foodtruckId: Long,
        mark: MarkRegistRequest
    ): Result<Boolean>

    suspend fun changeMarkRunState(
        markId: Long
    ): Result<Boolean>

    suspend fun deleteMark(
        markId: Long
    ): Result<String>
}