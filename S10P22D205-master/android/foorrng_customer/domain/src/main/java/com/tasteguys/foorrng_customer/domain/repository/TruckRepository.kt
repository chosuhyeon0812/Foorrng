package com.tasteguys.foorrng_customer.domain.repository

import com.tasteguys.foorrng_customer.domain.model.truck.FavoriteTruckData
import com.tasteguys.foorrng_customer.domain.model.truck.TruckData
import com.tasteguys.foorrng_customer.domain.model.truck.TruckDetailData
import com.tasteguys.foorrng_customer.domain.model.truck.TruckDetailMarkData
import com.tasteguys.foorrng_customer.domain.model.truck.TruckMenuData
import com.tasteguys.foorrng_customer.domain.model.truck.TruckOperationData
import com.tasteguys.foorrng_customer.domain.model.truck.TruckRegisterUpdateData
import java.io.File

interface TruckRepository {

    suspend fun reportFoodTruck(
        name: String,
        picture: File?,
        carNumber: String,
        announcement: String,
        phoneNumber: String,
        category: List<String>
    ): Result<TruckRegisterUpdateData>

    suspend fun updateFoodTruck(
        foodtruckId: Long,
        name: String,
        picture: File?,
        carNumber: String,
        announcement: String,
        phoneNumber: String,
        category: List<String>
    ): Result<TruckRegisterUpdateData>

    suspend fun reportToDeleteFoodTruck(truckId: Long): Result<String>

    suspend fun getTruckList(
        latLeft: Double,
        lngLeft: Double,
        latRight: Double,
        lngRight: Double
    ): Result<List<TruckData>>

    suspend fun getTruckDetail(
        truckId: Long
    ): Result<TruckDetailData>

    suspend fun markFavoriteTruck(
        truckId: Long
    ): Result<String>

    suspend fun getFavoriteTruckList(): Result<List<FavoriteTruckData>>

    suspend fun registerTruckInfo(
        truckId: Long,
        address: String,
        lat: Double,
        lng: Double,
        operationInfo: List<TruckOperationData>
    ): Result<TruckDetailMarkData>

    suspend fun registerReview(
        truckId: Long,
        rvIsDelicious: Boolean,
        rvIsCool: Boolean,
        rvIsClean: Boolean,
        rvIsKind: Boolean,
        rvIsSpecial: Boolean,
        rvIsCheap: Boolean,
        rvIsFast: Boolean
    ): Result<Long>

    suspend fun getMenu(
        truckId: Long
    ): Result<List<TruckMenuData>>

    suspend fun registerMenu(
        name: String,
        price: Long,
        truckId: Long,
        picture: File?
    ): Result<String>

    suspend fun updateMenu(
        id: Long,
        name: String,
        price: Long,
        truckId: Long,
        picture: File?
    ): Result<String>

    suspend fun deleteMenu(
        id: Long
    ): Result<String>

}