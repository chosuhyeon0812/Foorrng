package com.tasteguys.foorrng_customer.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.naver.maps.map.overlay.Marker
import com.tasteguys.foorrng_customer.presentation.model.MarkerWithData
import com.tasteguys.foorrng_customer.presentation.model.TruckDataWithAddress
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeMapViewModel @Inject constructor() : ViewModel(){

    var ownerAuthenticatedToggle = false

    fun toggleOwnerAuthenticate(){
        ownerAuthenticatedToggle = !ownerAuthenticatedToggle
        setTruckList()
    }

    var operatingToggle = false
    fun toggleOperating(){
        operatingToggle = !operatingToggle
        setTruckList()
    }

    var categoryList = mutableListOf<String>()
    var categoryToggle = false

    fun toggleCategory(){
        categoryToggle = !categoryToggle
        setTruckList()
    }

    var selectedMarker: Marker? = null

    var originList = mutableListOf<MarkerWithData>()

    private val _truckList = MutableLiveData<List<MarkerWithData>>(listOf())
    val truckList: LiveData<List<MarkerWithData>>
        get() = _truckList

    fun setTruckList(){
        clearMarkerList()
        val lst = originList.toMutableList()
        if(ownerAuthenticatedToggle){
            lst.retainAll{it.data.type=="Foodtruck"}
        }
        if(operatingToggle){
            lst.retainAll{it.data.isOperating}
        }
        if(categoryToggle){
            lst.retainAll{ it.data.category.toSet().intersect(categoryList.toSet()).isNotEmpty() }
        }
        _truckList.postValue(lst.toList())
    }

    private fun clearMarkerList(){
        for (marker in _truckList.value!!) {
            marker.marker.map = null
        }

    }
}