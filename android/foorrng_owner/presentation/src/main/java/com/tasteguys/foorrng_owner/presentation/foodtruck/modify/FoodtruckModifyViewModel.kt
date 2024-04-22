package com.tasteguys.foorrng_owner.presentation.foodtruck.modify

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasteguys.foorrng_owner.domain.usecase.foodtruck.UpdateFoodtruckUseCase
import com.tasteguys.foorrng_owner.presentation.base.PrefManager
import com.tasteguys.foorrng_owner.presentation.model.mapper.toMenu
import com.tasteguys.retrofit_adapter.FoorrngException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class FoodtruckModifyViewModel @Inject constructor(
    private val updateFoodtruckUseCase: UpdateFoodtruckUseCase,
    private val prefManager: PrefManager
) : ViewModel() {
    private var _foodtruckModifyResult = MutableLiveData<Result<Boolean>>()
    val foodtruckModifyResult
        get() = _foodtruckModifyResult


    fun updateFoodtruck(
        name: String,
        carNumber: String,
        accountInfo: String,
        phoneNumber: String,
        announcement: String,
        category: List<String>,
        picture: File?
    ) {
        viewModelScope.launch {
            val truckId = prefManager.foodtruckId
            if (truckId >= 0) {
                updateFoodtruckUseCase(
                    foodtruckId = truckId,
                    name = name,
                    carNumber = carNumber,
                    accountInfo = accountInfo,
                    phoneNumber = phoneNumber,
                    announcement = announcement,
                    category = category,
                    picture = picture
                ).let { _foodtruckModifyResult.postValue(it) }
            } else {
                _foodtruckModifyResult.postValue(
                    Result.failure(FoorrngException("","푸드트럭 정보 호출에 실패했습니다."))
                )
            }
        }
    }
}