package com.tasteguys.foorrng_customer.domain.usecase.truck

import com.tasteguys.foorrng_customer.domain.model.truck.TruckRegisterUpdateData
import com.tasteguys.foorrng_customer.domain.repository.TruckRepository
import java.io.File
import javax.inject.Inject

class UpdateTruckUseCase @Inject constructor(
    private val truckRepository: TruckRepository
) {
    suspend operator fun invoke(
        foodtruckId: Long,
        name: String,
        picture: File?,
        carNumber: String,
        announcement: String,
        phoneNumber: String,
        category: List<String>
    ): Result<TruckRegisterUpdateData>{
        return truckRepository.updateFoodTruck(
            foodtruckId, name, picture, carNumber, announcement, phoneNumber, category
        )
    }
}