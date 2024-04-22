package com.tasteguys.foorrng_customer.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasteguys.foorrng_customer.domain.usecase.food.GetCategoryUseCase
import com.tasteguys.foorrng_customer.domain.usecase.food.SelectFavoriteCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyFavoriteViewModel @Inject constructor(
    private val getCategoryUseCase: GetCategoryUseCase,
    private val selectFavoriteCategoryUseCase: SelectFavoriteCategoryUseCase
): ViewModel() {

    private val _getCategoryResult = MutableLiveData<Result<List<String>>>()
    val getCategoryResult: LiveData<Result<List<String>>>
        get() = _getCategoryResult

    private val _selectFavoriteCategoryResult = MutableLiveData<Result<Long?>>()
    val selectFavoriteCategoryResult: LiveData<Result<Long?>>
        get() = _selectFavoriteCategoryResult

    private val _selectedList = mutableSetOf<String>()
    val selectedList: MutableSet<String>
        get() = _selectedList

    fun getCategory(){
        viewModelScope.launch {
            getCategoryUseCase().let {
                _getCategoryResult.postValue(it)
            }
        }
    }

    fun selectFavoriteCategory(
        lat: Double, lng: Double
    ){
        viewModelScope.launch {
            selectFavoriteCategoryUseCase(lat, lng, _selectedList.toList()).let {
                _selectFavoriteCategoryResult.postValue(it)
            }
        }
    }


}