package com.tasteguys.foorrng_customer.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasteguys.foorrng_customer.domain.usecase.food.UpdateFavoriteCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteCategoryUpdateViewModel @Inject constructor(
    private val updateFavoriteCategoryUseCase: UpdateFavoriteCategoryUseCase
) : ViewModel() {

    private val _updateResult = MutableLiveData<Result<List<String>>>()
    val updateResult: LiveData<Result<List<String>>>
        get() = _updateResult

    val selectedList =  mutableSetOf<String>()

    fun updateCategory(
        lat: Double, lng: Double
    ){
        viewModelScope.launch {
            updateFavoriteCategoryUseCase(lat, lng, selectedList.toList()).let {
                _updateResult.postValue(it)
            }
        }
    }

}