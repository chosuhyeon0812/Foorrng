package com.tasteguys.foorrng_owner.presentation.foodtruck.regist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasteguys.foorrng_owner.domain.usecase.foodtruck.RegistFoodtruckUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class RegistFoodtruckViewModel @Inject constructor(
    private val registFoodtruckUseCase: RegistFoodtruckUseCase
) : ViewModel() {
    private var _registInfoResult = MutableLiveData<Result<Boolean>>()
    val registInfoResult: LiveData<Result<Boolean>>
        get() = _registInfoResult

    fun registFoodtruck(
        name: String,
        carNumber: String,
        accountInfo: String,
        phoneNumber: String,
        announcement: String,
        category: List<String>,
        picture: File?
    ) {
        viewModelScope.launch {
            registFoodtruckUseCase(
                name = name,
                carNumber = carNumber,
                accountInfo = accountInfo,
                phoneNumber = phoneNumber,
                announcement = announcement,
                category = category,
                picture = picture
            ).let { _registInfoResult.postValue(it) }
        }
    }
}