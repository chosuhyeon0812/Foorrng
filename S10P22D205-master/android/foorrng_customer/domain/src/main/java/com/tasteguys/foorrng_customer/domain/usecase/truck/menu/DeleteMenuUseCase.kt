package com.tasteguys.foorrng_customer.domain.usecase.truck.menu

import com.tasteguys.foorrng_customer.domain.repository.TruckRepository
import javax.inject.Inject

class DeleteMenuUseCase @Inject constructor(
    private val repository: TruckRepository
) {
    suspend operator fun invoke(id: Long) = repository.deleteMenu(id)
}