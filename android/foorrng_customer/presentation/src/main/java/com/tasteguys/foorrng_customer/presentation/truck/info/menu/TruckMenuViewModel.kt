package com.tasteguys.foorrng_customer.presentation.truck.info.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasteguys.foorrng_customer.domain.model.truck.TruckMenuData
import com.tasteguys.foorrng_customer.domain.usecase.truck.menu.DeleteMenuUseCase
import com.tasteguys.foorrng_customer.domain.usecase.truck.menu.GetMenuUseCase
import com.tasteguys.foorrng_customer.domain.usecase.truck.menu.RegisterMenuUseCase
import com.tasteguys.foorrng_customer.domain.usecase.truck.menu.UpdateMenuUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class TruckMenuViewModel @Inject constructor(
    private val registerMenuUseCase: RegisterMenuUseCase,
    private val updateMenuUseCase: UpdateMenuUseCase,
    private val deleteMenuUseCase: DeleteMenuUseCase,
    private val getMenuUseCase: GetMenuUseCase
): ViewModel() {

    var name = ""
    var price = 0L
    var img: File? = null

    private val _registerResult = MutableLiveData<Result<String>>()
    val registerResult: LiveData<Result<String>>
        get() = _registerResult

    private val _updateResult = MutableLiveData<Result<String>>()
    val updateResult: LiveData<Result<String>>
        get() = _updateResult

    private val _deleteResult = MutableLiveData<Result<String>>()
    val deleteResult: LiveData<Result<String>>
        get() = _deleteResult

    private val _getMenuListResult = MutableLiveData<Result<List<TruckMenuData>>>()
    val getMenuListResult: LiveData<Result<List<TruckMenuData>>>
        get() = _getMenuListResult

    fun registerMenu(truckId: Long){
        viewModelScope.launch {
            registerMenuUseCase(name, price, truckId, img).let {
                _registerResult.postValue(it)
            }
        }
    }

    fun updateMenu(id:Long, truckId: Long){
        viewModelScope.launch {
            updateMenuUseCase(id, name, price, truckId, img).let {
                _updateResult.postValue(it)
            }
        }
    }

    fun deleteMenu(id: Long){
        viewModelScope.launch {
            deleteMenuUseCase(id).let {
                _deleteResult.postValue(it)
            }
        }
    }

    fun getMenuList(truckId: Long){
        viewModelScope.launch {
            getMenuUseCase(truckId).let {
                _getMenuListResult.postValue(it)
            }
        }
    }

}