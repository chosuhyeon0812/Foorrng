package com.tasteguys.foorrng_owner.data.repository.foodtruck.remote

import com.tasteguys.foorrng_owner.data.api.FoodtruckService
import com.tasteguys.foorrng_owner.data.mapper.toNonDefault
import com.tasteguys.foorrng_owner.data.model.DefaultResponse
import com.tasteguys.foorrng_owner.data.model.foodtruck.FoodtruckDetailResponse
import com.tasteguys.foorrng_owner.data.model.foodtruck.FoodtruckRegistRequest
import com.tasteguys.foorrng_owner.data.model.foodtruck.FoodtruckUpdateRequest
import com.tasteguys.foorrng_owner.data.model.mark.MarkRegistRequest
import com.tasteguys.foorrng_owner.data.model.mark.MarkResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class FoodtruckRemoteDatasourceImpl @Inject constructor(
    private val foodtruckService: FoodtruckService
) : FoodtruckRemoteDatasource {
    override suspend fun registFoodtruck(
        name: String,
        carNumber: String,
        accountInfo: String,
        phoneNumber: String,
        announcement: String,
        category: List<String>,
        picture: File?
    ): Result<Boolean> {
        val image = picture?.asRequestBody("image/jpg".toMediaTypeOrNull())
        val multipartImage = image?.let {
            MultipartBody.Part.createFormData("picture", picture.name, it)
        }

        val request = FoodtruckRegistRequest(
            name = name,
            carNumber = carNumber,
            accountInfo = accountInfo,
            phoneNumber = phoneNumber,
            announcement = announcement,
            category = category
        )

        return foodtruckService.registFoodtruck(multipartImage, request)
            .toNonDefault()
            .map {
                it.foodtruckId >= 0L
            }
    }

    override suspend fun getFoodtruck(): Result<FoodtruckDetailResponse> {
        return foodtruckService.getFoodtruck().toNonDefault()
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
        val image = picture?.asRequestBody("image/jpg".toMediaTypeOrNull())
        val multipartImage = image?.let {
            MultipartBody.Part.createFormData("picture", picture.name, it)
        }

        val request = FoodtruckUpdateRequest(
            foodtruckId, announcement, name, accountInfo, carNumber, phoneNumber, category
        )

        return foodtruckService.updateFoodtruck(multipartImage, request)
            .toNonDefault()
            .map {
                it.foodtruckId >= 0L
            }
    }

    override suspend fun getMarkList(): Result<List<MarkResponse>> {
        return  foodtruckService.getMarkList().toNonDefault()
    }

    override suspend fun registMark(foodtruckId: Long,mark: MarkRegistRequest): Result<Boolean> {
        return foodtruckService.registMark(foodtruckId,mark)
            .toNonDefault()
            .map { response ->
                response.id >= 0L
            }
    }

    override suspend fun changeMarkRunState(markId: Long): Result<Boolean> {
        return foodtruckService.changeMarkRunState(markId)
            .toNonDefault()
            .map { response ->
                response.id >= 0L
            }
    }

    override suspend fun deleteMark(markId: Long): Result<String> {
        return foodtruckService.deleteMark(markId)
            .toNonDefault()
    }
}