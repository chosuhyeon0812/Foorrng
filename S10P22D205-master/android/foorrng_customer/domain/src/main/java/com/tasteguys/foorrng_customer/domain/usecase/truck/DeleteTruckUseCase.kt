package com.tasteguys.foorrng_customer.domain.usecase.truck

import com.tasteguys.foorrng_customer.domain.repository.TruckRepository
import javax.inject.Inject

class DeleteTruckUseCase @Inject constructor(
    private val repository: TruckRepository
) {
    suspend operator fun invoke(truckId: Long) = repository.reportToDeleteFoodTruck(truckId)
}