package com.tasteguys.foorrng_owner.presentation.location.manage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naver.maps.geometry.LatLng
import com.tasteguys.foorrng_owner.domain.usecase.mark.DeleteMarkUseCase
import com.tasteguys.foorrng_owner.domain.usecase.mark.GetMarkListUseCase
import com.tasteguys.foorrng_owner.presentation.model.location.RunInfo
import com.tasteguys.foorrng_owner.presentation.model.location.RunLocationInfo
import com.tasteguys.foorrng_owner.presentation.model.mapper.toRunLocationInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import javax.inject.Inject

@HiltViewModel
class LocationManageViewModel @Inject constructor(
    private val getMarkListUseCase: GetMarkListUseCase,
    private val deleteMarkUseCase: DeleteMarkUseCase
) : ViewModel() {
    private var _runLocationInfoListResult = MutableLiveData<Result<List<RunLocationInfo>>>()
    val runLocationInfoListResult: LiveData<Result<List<RunLocationInfo>>>
        get() = _runLocationInfoListResult

    private var _deleteMarkResult = MutableLiveData<Result<String>>()
    val deleteMarkResult: LiveData<Result<String>>
        get() = _deleteMarkResult

    fun getRunLocationInfoList() {
        viewModelScope.launch {
            getMarkListUseCase().let { result ->
                _runLocationInfoListResult.postValue(
                    result.map {
                        it.map { mark -> mark.toRunLocationInfo() }
                    }
                )
            }
        }
    }

    fun deleteMark(markId: Long) {
        viewModelScope.launch {
            deleteMarkUseCase(markId).let { result ->
                _deleteMarkResult.postValue(result)
            }
        }
    }

    fun dummyData(): List<RunLocationInfo> {
        return mutableListOf<RunLocationInfo>().apply {
            for (i in 0..10) {
                add(
                    RunLocationInfo(
                        -1,
                        "대구광역시 중구 명륜로 23길 80 $i",
                        LatLng(35.863585 + (i / 1000.0), 128.595737 + (i / 1000.0)),
                        false,
                        listOf(
                            RunInfo(
                                DayOfWeek.MONDAY,
                                "09:00",
                                "21:00",
                            ), RunInfo(
                                DayOfWeek.THURSDAY,
                                "09:00",
                                "21:00",
                            )
                        )
                    )
                )
            }
        }
    }

}