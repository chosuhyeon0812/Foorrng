package com.tasteguys.foorrng_customer.domain.usecase.truck.menu

import com.tasteguys.foorrng_customer.domain.repository.TruckRepository
import java.io.File
import javax.inject.Inject

class RegisterMenuUseCase @Inject constructor(
    private val repository: TruckRepository
) {
    suspend operator fun invoke(
        name: String, price: Long, truckId: Long, picture: File?
    ) = repository.registerMenu(name, price, truckId, picture)
}