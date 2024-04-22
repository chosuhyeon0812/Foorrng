package com.tasteguys.foorrng_owner.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasteguys.foorrng_owner.domain.model.user.LoginData
import com.tasteguys.foorrng_owner.domain.usecase.user.LoginUseCase
import com.tasteguys.foorrng_owner.domain.usecase.user.SignUpUseCase
import com.tasteguys.foorrng_owner.presentation.base.PrefManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val prefManager: PrefManager
) : ViewModel() {

    private var _loginResult = MutableLiveData<Result<LoginData>>()
    val loginResult: LiveData<Result<LoginData>>
        get() = _loginResult


    private var _signUpResult = MutableLiveData<Result<Long>>()
    val signUpResult: LiveData<Result<Long>>
        get() = _signUpResult


    fun login(userUid: Long, name: String, email: String) {
        viewModelScope.launch {
            loginUseCase(userUid, name, email).let {
                it.getOrNull()?.let {
                    prefManager.setUserId(userUid)
                    prefManager.setAccessToken(it.accessToken)
                    prefManager.setRefreshToken(it.refreshToken)
                }
                _loginResult.postValue(it)
            }
        }
    }

    fun signUp(userUid: Long, name: String, email: String) {
        viewModelScope.launch {
            signUpUseCase(userUid, name, email).let {
                _signUpResult.postValue(it)
            }
        }
    }
}