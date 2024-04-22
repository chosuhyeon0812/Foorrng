package com.tasteguys.foorrng_owner.presentation.location.recommend

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naver.maps.geometry.LatLng
import com.tasteguys.foorrng_owner.domain.usecase.recommend.getRecommendPlaceUseCase
import com.tasteguys.foorrng_owner.presentation.model.location.RecommendLocation
import com.tasteguys.foorrng_owner.presentation.model.mapper.toRecommendLocation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationRecommendViewModel @Inject constructor(
    private val recommendPlaceUseCase: getRecommendPlaceUseCase
) : ViewModel() {
    private var _recommendLocationList = MutableLiveData<Result<List<RecommendLocation>>>()
    val recommendLocationList: LiveData<Result<List<RecommendLocation>>>
        get() = _recommendLocationList

    fun getRecommendLocationList() {
        viewModelScope.launch {
            recommendPlaceUseCase().let { result ->
                _recommendLocationList.postValue(
                    result.map { list -> list.map { it.toRecommendLocation() } }
                )
            }
        }
    }
}