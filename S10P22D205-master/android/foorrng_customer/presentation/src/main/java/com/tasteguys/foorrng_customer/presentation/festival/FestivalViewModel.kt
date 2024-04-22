package com.tasteguys.foorrng_customer.presentation.festival

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naver.maps.geometry.LatLng
import com.tasteguys.foorrng_customer.domain.model.festival.FestivalData
import com.tasteguys.foorrng_customer.domain.usecase.festival.DeleteFestivalUseCase
import com.tasteguys.foorrng_customer.domain.usecase.festival.GetFestivalDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FestivalViewModel @Inject constructor(
    private val getFestivalDataUseCase: GetFestivalDataUseCase,
) : ViewModel(){

    private val _address = MutableLiveData("")
    val address: LiveData<String>
        get() = _address

    var latLng:LatLng = LatLng(0.0,0.0)

    fun setAddress(address: String){
        _address.postValue(address)
    }

    private val _tempAddress = MutableLiveData("")
    val tempAddress: LiveData<String>
        get() = _tempAddress

    fun setTempAddress(address: String){
        _tempAddress.postValue(address)
    }

    fun initAddress(){
        _tempAddress.value = ""
        _address.value = ""
        latLng = LatLng(0.0, 0.0)
    }

    private val _getListResult = MutableLiveData<Result<List<FestivalData>>>()
    val getListResult: LiveData<Result<List<FestivalData>>>
        get() = _getListResult

    private val _getDetailResult = MutableLiveData<Result<FestivalData>>()
    val getDetailResult: LiveData<Result<FestivalData>>
        get() = _getDetailResult

    fun getFestivalList(){
        viewModelScope.launch {
            getFestivalDataUseCase().let {
                _getListResult.postValue(it)
            }
        }
    }

    fun getFestivalDetail(id: Long){
        viewModelScope.launch {
            getFestivalDataUseCase(id).let {
                _getDetailResult.postValue(it)
            }
        }
    }



}