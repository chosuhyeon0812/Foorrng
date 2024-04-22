package com.tasteguys.foorrng_owner.presentation.foodtruck.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasteguys.foorrng_owner.domain.usecase.menu.RegistMenuUseCase
import com.tasteguys.foorrng_owner.domain.usecase.menu.UpdateMenuUseCase
import com.tasteguys.foorrng_owner.presentation.base.PrefManager
import com.tasteguys.foorrng_owner.presentation.model.foodtruck.Menu
import com.tasteguys.foorrng_owner.presentation.model.mapper.toMenu
import com.tasteguys.retrofit_adapter.FoorrngException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MenuEditViewModel @Inject constructor(
    private val registMenuUseCase: RegistMenuUseCase,
    private val updateMenuUseCase: UpdateMenuUseCase,
    // TODO private val deleteMenuUseCase: DeleteMenuUseCase
    private val prefManager: PrefManager
) : ViewModel() {
    private var _menuRegistResult = MutableLiveData<Result<Menu>>()
    val menuRegistResult
        get() = _menuRegistResult

    private var _menuModifyResult = MutableLiveData<Result<Menu>>()
    val menuModifyResult
        get() = _menuModifyResult

    fun registMenu(name: String, price: Int, picture: File?) {
        viewModelScope.launch {
            val truckId = prefManager.foodtruckId
            if (truckId >= 0) {
                registMenuUseCase(name, price.toLong(), truckId, picture).let { result ->
                    _menuRegistResult.postValue(
                        result.map { it.toMenu() }
                    )
                }
            } else {
                _menuRegistResult.postValue(
                    Result.failure(FoorrngException("","푸드트럭 정보 호출에 실패했습니다."))
                )
            }

        }
    }

    fun modifyMenu(menuid: Long, name: String, price: Int, picture: File?) {
        viewModelScope.launch {
            val truckId = prefManager.foodtruckId
            if (truckId >= 0) {
                updateMenuUseCase(menuid, name, price.toLong(), truckId, picture).let { result ->
                    _menuModifyResult.postValue(
                        result.map { it.toMenu() }
                    )
                }
            } else {
                _menuModifyResult.postValue(
                    Result.failure(FoorrngException("","푸드트럭 정보 호출에 실패했습니다."))
                )
            }

        }
    }
}