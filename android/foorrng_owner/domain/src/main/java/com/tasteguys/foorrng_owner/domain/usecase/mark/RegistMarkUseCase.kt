package com.tasteguys.foorrng_owner.domain.usecase.mark

import com.tasteguys.foorrng_owner.domain.model.mark.MarkData
import com.tasteguys.foorrng_owner.domain.repository.FoodtruckRepository
import javax.inject.Inject

class RegistMarkUseCase @Inject constructor(
    private val foodtruckRepository: FoodtruckRepository
) {
    suspend operator fun invoke(
        foodtruckId: Long,
        markData: MarkData
    ) = foodtruckRepository.registMark(foodtruckId,markData)
}