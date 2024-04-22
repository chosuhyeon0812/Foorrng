package com.tasteguys.foorrng_owner.domain.usecase.mark

import com.tasteguys.foorrng_owner.domain.repository.FoodtruckRepository
import javax.inject.Inject

class ChangeMarkStateUseCase @Inject constructor(
    private val foodtruckRepository: FoodtruckRepository
) {
    suspend operator fun invoke(markId: Long) = foodtruckRepository.changeMarkRunState(markId)
}