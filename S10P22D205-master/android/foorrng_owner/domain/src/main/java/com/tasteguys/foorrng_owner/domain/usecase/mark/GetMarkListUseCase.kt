package com.tasteguys.foorrng_owner.domain.usecase.mark

import com.tasteguys.foorrng_owner.domain.repository.FoodtruckRepository
import javax.inject.Inject

class GetMarkListUseCase @Inject constructor(
    private val foodtruckRepository: FoodtruckRepository
) {
    suspend operator fun invoke() = foodtruckRepository.getMarkList()
}