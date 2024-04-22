package com.tasteguys.foorrng_customer.domain.usecase.truck

import com.tasteguys.foorrng_customer.domain.model.truck.FavoriteTruckData
import com.tasteguys.foorrng_customer.domain.repository.TruckRepository
import javax.inject.Inject

class GetTruckListUseCase @Inject constructor(
    private val truckRepository: TruckRepository
) {
    suspend operator fun invoke(
        latLeft: Double,
        lngLeft: Double,
        latRight: Double,
        lngRight: Double
    ) = truckRepository.getTruckList(
        latLeft, lngLeft, latRight, lngRight
    )

    suspend operator fun invoke(): Result<List<FavoriteTruckData>> {
        return truckRepository.getFavoriteTruckList()
    }
}