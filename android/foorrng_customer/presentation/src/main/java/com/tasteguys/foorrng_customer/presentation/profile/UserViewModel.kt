package com.tasteguys.foorrng_customer.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasteguys.foorrng_customer.domain.model.user.UserData
import com.tasteguys.foorrng_customer.domain.usecase.user.GetUserDataUseCase
import com.tasteguys.foorrng_customer.presentation.model.FavoriteCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUserDataUseCase: GetUserDataUseCase
): ViewModel() {

    private val _getUserResult = MutableLiveData<Result<UserData>>()
    val getUserResult: LiveData<Result<UserData>>
        get() = _getUserResult

    private val _foodCategory = mutableMapOf<String, Boolean>()
    val foodCategory: MutableMap<String, Boolean>
        get() = _foodCategory

    fun getUserData(){
        viewModelScope.launch {
            getUserDataUseCase().let {
                _getUserResult.postValue(it)
            }
        }
    }
}