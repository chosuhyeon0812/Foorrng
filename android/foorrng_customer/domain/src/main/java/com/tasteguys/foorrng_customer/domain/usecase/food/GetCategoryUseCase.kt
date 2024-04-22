package com.tasteguys.foorrng_customer.domain.usecase.food

import com.tasteguys.foorrng_customer.domain.repository.FoodCategoryRepository
import javax.inject.Inject

class GetCategoryUseCase @Inject constructor(
    private val foodCategoryRepository: FoodCategoryRepository
) {
    suspend operator fun invoke() = foodCategoryRepository.getCategory()
}