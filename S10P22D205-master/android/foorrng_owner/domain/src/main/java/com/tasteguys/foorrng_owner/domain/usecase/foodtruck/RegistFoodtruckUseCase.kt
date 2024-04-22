package com.tasteguys.foorrng_owner.domain.usecase.foodtruck

import com.tasteguys.foorrng_owner.domain.repository.FoodtruckRepository
import java.io.File
import javax.inject.Inject

class RegistFoodtruckUseCase @Inject constructor(
    private val foodtruckRepository: FoodtruckRepository
) {
    suspend operator fun invoke(
        name: String,
        carNumber: String,
        accountInfo: String,
        phoneNumber: String,
        announcement: String,
        category: List<String>,
        picture: File?
    ) = foodtruckRepository.registFoodtruck(
        name = name,
        carNumber = carNumber,
        accountInfo = accountInfo,
        phoneNumber = phoneNumber,
        announcement = announcement,
        category = category,
        picture = picture
    )
}