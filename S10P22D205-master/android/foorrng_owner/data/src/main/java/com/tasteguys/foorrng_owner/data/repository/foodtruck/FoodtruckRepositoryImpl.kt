package com.tasteguys.foorrng_owner.data.repository.foodtruck

import com.tasteguys.foorrng_owner.data.mapper.toFoodtruckInfoData
import com.tasteguys.foorrng_owner.data.mapper.toMarkData
import com.tasteguys.foorrng_owner.data.mapper.toMarkRegistRequest
import com.tasteguys.foorrng_owner.data.repository.foodtruck.remote.FoodtruckRemoteDatasource
import com.tasteguys.foorrng_owner.domain.model.foodtruck.FoodtruckInfoData
import com.tasteguys.foorrng_owner.domain.model.mark.MarkData
import com.tasteguys.foorrng_owner.domain.repository.FoodtruckRepository
import java.io.File
import javax.inject.Inject

class FoodtruckRepositoryImpl @Inject constructor(
    private val foodtruckRemoteDatasource: FoodtruckRemoteDatasource
) : FoodtruckRepository {
    override suspend fun registFoodtruck(
        name: String,
        carNumber: String,
        accountInfo: String,
        phoneNumber: String,
        announcement: String,
        category: List<String>,
        picture: File?
    ): Result<Boolean> {
        return foodtruckRemoteDatasource.registFoodtruck(
            name = name,
            carNumber = carNumber,
            accountInfo = accountInfo,
            phoneNumber = phoneNumber,
            announcement = announcement,
            category = category,
            picture = picture
        )
    }

    override suspend fun getFoodtruck(): Result<FoodtruckInfoData> {
        return foodtruckRemoteDatasource.getFoodtruck().map { it.toFoodtruckInfoData() }
    }

    override suspend fun updateFoodtruck(
        foodtruckId: Long,
        name: String,
        carNumber: String,
        accountInfo: String,
        phoneNumber: String,
        announcement: String,
        category: List<String>,
        picture: File?
    ): Result<Boolean> {
        return foodtruckRemoteDatasource.updateFoodtruck(
            foodtruckId = foodtruckId,
            name = name,
            carNumber = carNumber,
            accountInfo = accountInfo,
            phoneNumber = phoneNumber,
            announcement = announcement,
            category = category,
            picture = picture
        )
    }

    override suspend fun getMarkList(): Result<List<MarkData>> {
        return foodtruckRemoteDatasource.getMarkList().map { list ->
            list.map { it.toMarkData() }
        }
    }

    override suspend fun registMark(foodtruckId: Long,markData: MarkData): Result<Boolean> {
        return foodtruckRemoteDatasource.registMark(foodtruckId,markData.toMarkRegistRequest())
    }

    override suspend fun changeMarkRunState(markId: Long): Result<Boolean> {
        return foodtruckRemoteDatasource.changeMarkRunState(markId)
    }

    override suspend fun deleteMark(markId: Long): Result<String> {
        return foodtruckRemoteDatasource.deleteMark(markId)
    }
}