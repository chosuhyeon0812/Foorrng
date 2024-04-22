package com.tasteguys.foorrng_customer.domain.usecase.food

import com.tasteguys.foorrng_customer.domain.repository.FoodCategoryRepository
import javax.inject.Inject

class UpdateFavoriteCategoryUseCase @Inject constructor(
    private val repository: FoodCategoryRepository
) {
    suspend operator fun invoke(
        lat: Double, lng: Double, category: List<String>
    ) = repository.updateFavoriteCategory(lat, lng, category)
}