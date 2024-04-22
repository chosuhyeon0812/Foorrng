package com.tasteguys.foorrng_owner.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasteguys.foorrng_owner.domain.usecase.foodtruck.GetFoodtruckInfoUseCase
import com.tasteguys.foorrng_owner.presentation.base.Event
import com.tasteguys.foorrng_owner.presentation.base.PrefManager
import com.tasteguys.foorrng_owner.presentation.model.foodtruck.FoodTruckInfo
import com.tasteguys.foorrng_owner.presentation.model.mapper.toFoodtruckInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getFoodtruckInfoUseCase: GetFoodtruckInfoUseCase,
    private val prefManager: PrefManager
): ViewModel() {
    private var _toolbarController = MutableLiveData<MainToolbarControl>()
    val toolbarController: LiveData<MainToolbarControl>
        get() = _toolbarController

    private var _foodtruckInfo = MutableLiveData<Event<Result<FoodTruckInfo>>>()
    val foodtruckInfo: LiveData<Event<Result<FoodTruckInfo>>>
        get() = _foodtruckInfo

    fun changeToolbar(control: MainToolbarControl){
        _toolbarController.value = control
    }

    fun getFoodtruckInfo(){
        viewModelScope.launch {
            getFoodtruckInfoUseCase().let { result ->
                result.onSuccess {
                    prefManager.foodtruckId = it.foodtruckId
                }
                _foodtruckInfo.postValue(
                    Event(
                        result.map { it.toFoodtruckInfo() }
                    )
                )
            }
        }
    }
}