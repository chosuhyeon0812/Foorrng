package com.tasteguys.foorrng_customer.domain.usecase.truck

import com.tasteguys.foorrng_customer.domain.repository.TruckRepository
import javax.inject.Inject

class MarkTruckDetailUseCase @Inject constructor(
    private val repository: TruckRepository
) {
    suspend operator fun invoke(
        truckId: Long
    ): Result<String> {
        return repository.markFavoriteTruck(truckId)
    }
}