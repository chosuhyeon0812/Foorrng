package com.tasteguys.foorrng_owner.presentation.location.open

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasteguys.foorrng_owner.domain.usecase.mark.ChangeMarkStateUseCase
import com.tasteguys.foorrng_owner.domain.usecase.mark.GetMarkListUseCase
import com.tasteguys.foorrng_owner.presentation.model.location.RunLocationInfo
import com.tasteguys.foorrng_owner.presentation.model.mapper.toRunLocationInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodtruckOpenViewModel @Inject constructor(
    private val getMarkListUseCase: GetMarkListUseCase,
    private val markStateUseCase: ChangeMarkStateUseCase,
) : ViewModel() {
    private var _runLocationInfoListResult = MutableLiveData<Result<List<RunLocationInfo>>>()
    val runLocationInfoListResult: LiveData<Result<List<RunLocationInfo>>>
        get() = _runLocationInfoListResult

    private var _changeMarkStateResult = MutableLiveData<Result<Boolean>>()
    val changeMarkStateResult: LiveData<Result<Boolean>>
        get() = _changeMarkStateResult

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

    fun changeOpenStateRunLocationInfo(markId: Long) {
        viewModelScope.launch {
            markStateUseCase(markId).let { result ->
                _changeMarkStateResult.postValue(result)
            }
        }
    }

}