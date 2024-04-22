package com.tasteguys.foorrng_owner.presentation.foodtruck.menu

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasteguys.foorrng_owner.domain.usecase.menu.GetMenuListUseCase
import com.tasteguys.foorrng_owner.presentation.base.PrefManager
import com.tasteguys.foorrng_owner.presentation.model.foodtruck.Menu
import com.tasteguys.foorrng_owner.presentation.model.mapper.toMenu
import com.tasteguys.retrofit_adapter.FoorrngException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.http.GET
import javax.inject.Inject

private const val TAG = "FoodtruckMenuViewModel_poorrng"
@HiltViewModel
class FoodtruckMenuViewModel @Inject constructor(
    private val getMenuListUseCase: GetMenuListUseCase,
    private val prefManager: PrefManager
) : ViewModel() {
    private var _menuList = MutableLiveData<Result<List<Menu>>>()
    val menuList: LiveData<Result<List<Menu>>>
        get() = _menuList

    fun getMenuList() {
        viewModelScope.launch {
            val id = prefManager.foodtruckId
            Log.d(TAG, "getMenuList: $id")
            if (id >= 0){
                getMenuListUseCase(id).let { result ->
                    _menuList.postValue(
                        result.map { it.map { menuData -> menuData.toMenu() } }
                    )
                }
            } else {
                _menuList.postValue(
                    Result.failure(FoorrngException("","푸드트럭 정보 호출에 실패했습니다."))
                )
            }
        }
    }


    private val dummyMenuList : () -> (Result<List<Menu>>) = {
        Result.success(mutableListOf<Menu>().apply {
            for (i in 0..10) {
                this.add(
                    Menu(
                        0,
                        "data:image/jpeg;base64,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",
                        "박명수 짤",
                        10000000
                    )
                )
            }
        })
    }
}