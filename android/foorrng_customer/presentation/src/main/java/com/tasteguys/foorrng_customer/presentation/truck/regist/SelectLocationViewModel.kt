package com.tasteguys.foorrng_customer.presentation.truck.regist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class SelectLocationViewModel @Inject constructor() : ViewModel(){
    private val _markAddress = MutableLiveData("")
    val markAddress: LiveData<String>
        get() = _markAddress

    private val _markLat = MutableLiveData<Double>()
    val markLat: LiveData<Double>
        get() = _markLat

    private val _markLng = MutableLiveData<Double>()
    val markLng: LiveData<Double>
        get() = _markLng

    fun setMark(name: String, lat: Double, lng: Double){
        _markAddress.postValue(name)
        _markLat.postValue(lat)
        _markLng.postValue(lng)
    }
}