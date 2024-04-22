package com.tasteguys.foorrng_owner.presentation.busi_number

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasteguys.foorrng_owner.domain.usecase.user.RegisteBusiNumberUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusiNumberViewModel @Inject constructor(
    private val registeBusiNumberUseCase: RegisteBusiNumberUseCase
) : ViewModel() {
    private var _registResult = MutableLiveData<Result<String>>()
    val registResult: LiveData<Result<String>>
        get() = _registResult


    fun registBusinessNumber(businessNumber: String) {
        viewModelScope.launch {
            registeBusiNumberUseCase(businessNumber).let {
                _registResult.postValue(it)
            }
        }
    }
}