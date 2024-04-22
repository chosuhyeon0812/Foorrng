package com.tasteguys.foorrng_customer.domain.usecase.festival

import com.tasteguys.foorrng_customer.domain.repository.FestivalRepository
import javax.inject.Inject

class GetFestivalDataUseCase @Inject constructor(
    private val repository: FestivalRepository
) {

    suspend operator fun invoke() = repository.getFestivalList()
    suspend operator fun invoke(id: Long) = repository.getFestivalDetail(id)

}