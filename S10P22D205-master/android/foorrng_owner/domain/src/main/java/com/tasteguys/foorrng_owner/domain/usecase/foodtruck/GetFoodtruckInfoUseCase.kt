package com.tasteguys.foorrng_owner.domain.usecase.foodtruck

import com.tasteguys.foorrng_owner.domain.model.foodtruck.FoodtruckInfoData
import com.tasteguys.foorrng_owner.domain.repository.FoodtruckRepository
import javax.inject.Inject

class GetFoodtruckInfoUseCase @Inject constructor(
    private val foodtruckRepository: FoodtruckRepository
){
    suspend operator fun invoke(): Result<FoodtruckInfoData> {
        return foodtruckRepository.getFoodtruck()
    }
}