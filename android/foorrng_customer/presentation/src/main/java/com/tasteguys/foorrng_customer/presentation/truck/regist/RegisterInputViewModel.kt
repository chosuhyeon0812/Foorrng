package com.tasteguys.foorrng_customer.presentation.truck.regist

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasteguys.foorrng_customer.domain.model.truck.TruckDetailMarkData
import com.tasteguys.foorrng_customer.domain.model.truck.TruckRegisterUpdateData
import com.tasteguys.foorrng_customer.domain.usecase.truck.MarkTruckDetailUseCase
import com.tasteguys.foorrng_customer.domain.usecase.truck.RegisterTruckUseCase
import com.tasteguys.foorrng_customer.domain.usecase.truck.UpdateTruckUseCase
import com.tasteguys.foorrng_customer.presentation.base.toFile
import com.tasteguys.foorrng_customer.presentation.model.FavoriteCategory
import com.tasteguys.foorrng_customer.presentation.model.MapMark
import com.tasteguys.foorrng_customer.presentation.model.RunDay
import com.tasteguys.foorrng_customer.presentation.model.TruckOperationInfo
import com.tasteguys.foorrng_customer.presentation.model.mapper.toDomain
import com.tasteguys.foorrng_customer.presentation.model.toKorean
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import java.time.DayOfWeek
import javax.inject.Inject

private const val TAG = "RegisterInputViewModel"

@HiltViewModel
class RegisterInputViewModel @Inject constructor(
    private val registerUseCase: RegisterTruckUseCase,
    private val updateTruckUseCase: UpdateTruckUseCase,
    private val markTruckDetailUseCase: MarkTruckDetailUseCase,
) : ViewModel() {

    var name = ""

    private val _picture = MutableLiveData<Uri?>(null)
    val picture: LiveData<Uri?>
        get() = _picture

    fun inputPicture(input: Uri) {
        _picture.postValue(input)
    }

    var loadedPicture: String? = null

    var carNumber = ""
    var announcement = ""
    var phoneNumber = ""

    private val _foodCategory = mutableMapOf<String, Boolean>()
    val foodCategory: MutableMap<String, Boolean>
        get() = _foodCategory

    private val _category = MutableLiveData(mutableListOf<FavoriteCategory>())
    val category: LiveData<MutableList<FavoriteCategory>>
        get() = _category


    private val _markAddress = MutableLiveData("")
    val markAddress: LiveData<String>
        get() = _markAddress

    private val _markLat = MutableLiveData<Double>()
    val markLat: LiveData<Double>
        get() = _markLat

    private val _markLng = MutableLiveData<Double>()
    val markLng: LiveData<Double>
        get() = _markLng

    private val _markInfo = MutableLiveData<MapMark>()
    val markInfo: LiveData<MapMark>
        get() = _markInfo

    fun setMark(name: String, lat: Double, lng: Double) {
        _markAddress.postValue(name)
        _markLat.postValue(lat)
        _markLng.postValue(lng)
        _markInfo.postValue(MapMark(name, lat, lng))
    }

    fun initData() {
        name = ""
        _picture.value = null
        carNumber = ""
        announcement = ""
        phoneNumber = ""
        _markAddress.value = ""
        _markInfo.value = MapMark("", -1.0, -1.0)
        _category.value = _category.value!!.map { FavoriteCategory(it.name, false) }.toMutableList()
    }

    var inputState: Boolean = false
    var imageChanged: Boolean = false


    private var _runDayList = MutableLiveData<Map<DayOfWeek, RunDay>>()
    val runDayList: LiveData<Map<DayOfWeek, RunDay>>
        get() = _runDayList

    fun addRunDay(runDay: RunDay) {
        val list = _runDayList.value?.toMutableMap() ?: mutableMapOf()
        list[runDay.day] = runDay
        Log.d(TAG, "addRunDay: $list")
        _runDayList.value = list
    }

    fun deleteRunDay(runDay: DayOfWeek) {
        val list = _runDayList.value?.toMutableMap() ?: mutableMapOf()
        list.remove(runDay)
        _runDayList.value = list
    }

    private val _registerResult = MutableLiveData<Result<TruckRegisterUpdateData>>()
    val registerResult: LiveData<Result<TruckRegisterUpdateData>>
        get() = _registerResult

    private val _updateResult = MutableLiveData<Result<TruckRegisterUpdateData>>()
    val updateResult: LiveData<Result<TruckRegisterUpdateData>>
        get() = _updateResult

    private val _markRegisterResult = MutableLiveData<Result<TruckDetailMarkData>>()
    val markRegisterResult: LiveData<Result<TruckDetailMarkData>>
        get() = _markRegisterResult


    private val _markFavoriteTruckResult = MutableLiveData<Result<String>>()
    val markFavoriteTruckResult: LiveData<Result<String>>
        get() = _markFavoriteTruckResult

    fun registerTruck(
        name: String,
        picture: File?,
        carNumber: String,
        announcement: String,
        phoneNumber: String,
        category: List<String>
    ) {
        viewModelScope.launch {
            registerUseCase(
                name, picture, carNumber, announcement, phoneNumber, category
            ).let { res ->
                _registerResult.postValue(res)
            }
        }
    }

    fun registerOperationInfo(
        id: Long
    ) {

        viewModelScope.launch {
            registerUseCase(
                id,
                markAddress.value!!,
                markLat.value!!,
                markLng.value!!,
                _runDayList.value!!.values.toList()
                    .map { TruckOperationInfo(it.day.toKorean(), it.startTime, it.endTime) }
                    .map { it.toDomain() }
            ).let {
                _markRegisterResult.postValue(it)
            }
        }
    }

    fun updateTruck(
        foodtruckId: Long,
        name: String,
        picture: File?,
        carNumber: String,
        announcement: String,
        phoneNumber: String,
        category: List<String>
    ) {
        viewModelScope.launch {
            updateTruckUseCase(
                foodtruckId, name, picture, carNumber, announcement, phoneNumber, category
            ).let {
                _updateResult.postValue(it)
            }
        }
    }

    fun markFavoriteTruck(
        truckId: Long
    ){
        viewModelScope.launch {
            markTruckDetailUseCase(truckId).let {
                _markFavoriteTruckResult.postValue(it)
            }
        }
    }

}