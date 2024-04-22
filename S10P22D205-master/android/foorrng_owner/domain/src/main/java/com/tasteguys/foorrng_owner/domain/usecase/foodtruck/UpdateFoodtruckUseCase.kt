package com.tasteguys.foorrng_owner.domain.usecase.foodtruck

import com.tasteguys.foorrng_owner.domain.repository.FoodtruckRepository
import java.io.File
import javax.inject.Inject

class UpdateFoodtruckUseCase @Inject constructor(
    private val foodtruckRepository: FoodtruckRepository
) {
    suspend operator fun invoke(
        foodtruckId: Long,
        name: String,
        carNumber: String,
        accountInfo: String,
        phoneNumber: String,
        announcement: String,
        category: List<String>,
        picture: File?
    ) = foodtruckRepository.updateFoodtruck(
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