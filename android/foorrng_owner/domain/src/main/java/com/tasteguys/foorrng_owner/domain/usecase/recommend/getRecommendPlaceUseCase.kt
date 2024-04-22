package com.tasteguys.foorrng_owner.domain.usecase.recommend

import com.tasteguys.foorrng_owner.domain.repository.RecommendRepository
import javax.inject.Inject

class getRecommendPlaceUseCase @Inject constructor(
    private val recommendRepository: RecommendRepository
) {
    suspend operator fun invoke() = recommendRepository.getRecommendList()
}