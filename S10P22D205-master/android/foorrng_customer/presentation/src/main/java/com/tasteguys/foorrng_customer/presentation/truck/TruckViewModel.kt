package com.tasteguys.foorrng_customer.presentation.truck

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasteguys.foorrng_customer.domain.model.truck.TruckData
import com.tasteguys.foorrng_customer.domain.model.truck.TruckDetailData
import com.tasteguys.foorrng_customer.domain.model.truck.TruckDetailMarkData
import com.tasteguys.foorrng_customer.domain.model.truck.TruckRegisterUpdateData
import com.tasteguys.foorrng_customer.domain.usecase.truck.DeleteTruckUseCase
import com.tasteguys.foorrng_customer.domain.usecase.truck.GetTruckDetailUseCase
import com.tasteguys.foorrng_customer.domain.usecase.truck.GetTruckListUseCase
import com.tasteguys.foorrng_customer.domain.usecase.truck.MarkTruckDetailUseCase
import com.tasteguys.foorrng_customer.domain.usecase.truck.RegisterTruckUseCase
import com.tasteguys.foorrng_customer.domain.usecase.truck.UpdateTruckUseCase
import com.tasteguys.foorrng_customer.presentation.base.PrefManager
import com.tasteguys.foorrng_customer.presentation.model.FavoriteCategory
import com.tasteguys.foorrng_customer.presentation.model.FavoriteTruck
import com.tasteguys.foorrng_customer.presentation.model.TruckDataWithAddress
import com.tasteguys.foorrng_customer.presentation.model.TruckOperationInfo
import com.tasteguys.foorrng_customer.presentation.model.mapper.toDomain
import com.tasteguys.foorrng_customer.presentation.model.mapper.toFavoriteTruck
import com.tasteguys.foorrng_customer.presentation.model.mapper.toTruckDataWithAddress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

private const val TAG = "TruckViewModel"
@HiltViewModel
class TruckViewModel @Inject constructor(
    private val getTruckListUseCase: GetTruckListUseCase,
    private val getTruckDetailUseCase: GetTruckDetailUseCase,
    private val markTruckDetailUseCase: MarkTruckDetailUseCase,
    private val deleteTruckUseCase: DeleteTruckUseCase
) : ViewModel() {

    private val _truckListResult = MutableLiveData<Result<List<TruckDataWithAddress>>>()
    val truckListResult: LiveData<Result<List<TruckDataWithAddress>>>
        get() = _truckListResult

    private val _truckDetailResult = MutableLiveData<Result<TruckDetailData>>()
    val truckDetailResult: LiveData<Result<TruckDetailData>>
        get() = _truckDetailResult

    private val _deleteTruckResult = MutableLiveData<Result<String>>()
    val deleteTruckResult: LiveData<Result<String>>
        get() = _deleteTruckResult

    private val _markFavoriteTruckResult = MutableLiveData<Result<String>>()
    val markFavoriteTruckResult: LiveData<Result<String>>
        get() = _markFavoriteTruckResult

    private val _favoriteTruckListResult = MutableLiveData<Result<List<TruckDataWithAddress>>>()
    val favoriteTruckListResult: LiveData<Result<List<TruckDataWithAddress>>>
        get() = _favoriteTruckListResult

    var ownerAuthenticated = false
    var isOperating = false
    var isFavorite = false

    fun getTruckList(
        latLeft: Double, lngLeft: Double, latRight: Double, lngRight: Double
    ) {
        viewModelScope.launch {
            getTruckListUseCase(
                latLeft, lngLeft, latRight, lngRight
            ).let { result ->
                _truckListResult.postValue(
                    result.map { list ->
                        list.map { it.toTruckDataWithAddress() }
                    }
                )
            }
        }
    }

    fun getTruckDetail(
        truckId: Long
    ) {
        viewModelScope.launch {
            getTruckDetailUseCase(truckId).let {
                _truckDetailResult.postValue(it)
            }
        }
    }

    fun deleteTruck(truckId: Long){
        viewModelScope.launch {
            deleteTruckUseCase(truckId).let {
                _deleteTruckResult.postValue(it)
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

    fun getFavoriteTruckList(){
        viewModelScope.launch {
            getTruckListUseCase().let { result->
                _favoriteTruckListResult.postValue(result.map { list ->
                    list.map { it.toTruckDataWithAddress() }
                })
            }
        }
    }

}