package com.tasteguys.foorrng_customer.domain.usecase.food

import com.tasteguys.foorrng_customer.domain.repository.FoodCategoryRepository
import javax.inject.Inject

class SelectFavoriteCategoryUseCase @Inject constructor(
    private val foodCategoryRepository: FoodCategoryRepository
) {
    suspend operator fun invoke(
        lat: Double, lng: Double, category: List<String>
    ) = foodCategoryRepository.selectFavoriteCategory(lat, lng, category)
}