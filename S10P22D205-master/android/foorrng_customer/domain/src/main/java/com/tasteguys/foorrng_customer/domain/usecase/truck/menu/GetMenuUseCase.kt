package com.tasteguys.foorrng_customer.domain.usecase.truck.menu

import com.tasteguys.foorrng_customer.domain.repository.TruckRepository
import javax.inject.Inject

class GetMenuUseCase @Inject constructor(
    private val repository: TruckRepository
) {
    suspend operator fun invoke(truckId: Long) = repository.getMenu(truckId)
}