package com.tasteguys.foorrng_customer.domain.usecase.festival

import com.tasteguys.foorrng_customer.domain.repository.FestivalRepository
import javax.inject.Inject

class DeleteFestivalUseCase @Inject constructor(
    private val repository: FestivalRepository
) {
    suspend operator fun invoke(id: Long) = repository.deleteFestival(id)
}