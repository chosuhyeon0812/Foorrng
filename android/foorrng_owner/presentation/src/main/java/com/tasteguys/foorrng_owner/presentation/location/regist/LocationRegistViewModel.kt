package com.tasteguys.foorrng_owner.presentation.location.regist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasteguys.foorrng_owner.domain.usecase.mark.RegistMarkUseCase
import com.tasteguys.foorrng_owner.presentation.base.PrefManager
import com.tasteguys.foorrng_owner.presentation.model.mapper.getMarkDataFromRunLocationRunDay
import com.tasteguys.foorrng_owner.presentation.model.run.RunDay
import com.tasteguys.foorrng_owner.presentation.model.run.RunLocation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationRegistViewModel @Inject constructor(
    private val locationRegistUseCase: RegistMarkUseCase,
    private val prefManager: PrefManager
) : ViewModel() {
    private var _runLocation = MutableLiveData<RunLocation>()
    val runLocation: LiveData<RunLocation>
        get() = _runLocation

    private var _runDayList = MutableLiveData<List<RunDay>>()
    val runDayList: LiveData<List<RunDay>>
        get() = _runDayList

    private var _registResult = MutableLiveData<Result<Boolean>>()
    val registResult: LiveData<Result<Boolean>>
        get() = _registResult

    fun setRunLocation(runLocation: RunLocation) {
        _runLocation.postValue(runLocation)
    }

    fun addRunDay(runDay: RunDay){
        val list = _runDayList.value?.toMutableList() ?: mutableListOf()
        list.add(runDay)
        _runDayList.value = list
    }

    fun deleteRunDay(runDay: RunDay){
        val list = _runDayList.value?.toMutableList() ?: mutableListOf()
        list.remove(runDay)
        _runDayList.value = list
    }

    fun registRunLocationInfo(){
        viewModelScope.launch {
            val foodtruckId = prefManager.foodtruckId
            val runLocation = _runLocation.value
            val runDayList = _runDayList.value
            if(runDayList.isNullOrEmpty()){
                _registResult.postValue(
                    Result.failure(Exception("최소 하나의 운영일을 선택해주세요."))
                )
            }
            else if(runLocation != null && runDayList != null && foodtruckId != -1L){
                val markData = getMarkDataFromRunLocationRunDay(runDayList, runLocation)
                locationRegistUseCase(foodtruckId,markData).let {
                    _registResult.postValue(it)
                }
            } else {
                _registResult.postValue(Result.failure(Exception("정보 호출에 실패했습니다.잠시후 다시 시도해주세요.")))
            }
        }
    }
}