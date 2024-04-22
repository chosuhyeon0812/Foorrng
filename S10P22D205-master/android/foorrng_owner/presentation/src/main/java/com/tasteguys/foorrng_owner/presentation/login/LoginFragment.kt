package com.tasteguys.foorrng_owner.presentation.login

import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.kakao.sdk.user.UserApiClient
import com.tasteguys.foorrng_owner.presentation.LoginActivity
import com.tasteguys.foorrng_owner.presentation.main.MainActivity
import com.tasteguys.foorrng_owner.presentation.R
import com.tasteguys.foorrng_owner.presentation.base.BaseFragment
import com.tasteguys.foorrng_owner.presentation.databinding.FragmentLoginBinding
import com.tasteguys.retrofit_adapter.FoorrngException
import dagger.hilt.android.AndroidEntryPoint
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

private const val TAG = "LoginFragment_poorrng"

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::bind, R.layout.fragment_login
) {
    private val loginViewModel: LoginViewModel by viewModels()

    private lateinit var loginActivity: LoginActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginActivity = _activity as LoginActivity

        loginActivity.onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    loginActivity.finish()
                }
            }
        )

        setListener()
        setObserver()
    }

    private fun setListener() {
        binding.ivKakaoLogin.setOnClickListener {
            showLoading()
            kakaologin { uId, name, email ->
                loginViewModel.login(uId, name, email)
            }
        }
    }

    private fun setObserver() {
        loginViewModel.loginResult.observe(viewLifecycleOwner) {
            hideLoading()
            if (it.isSuccess) {
                startActivity(Intent(_activity, MainActivity::class.java).apply {
                    putExtra("is_regist",it.getOrNull()?.isBusiRegist ?: false)
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                })
            } else {
                it.exceptionOrNull()?.let { t ->
                    if (t is FoorrngException && t.code == "U-001") {
                        showSignUpDialog()
                    } else {
                        showSnackBar("로그인에 실패했습니다. :(")
                    }
                }
            }
        }

        loginViewModel.signUpResult.observe(viewLifecycleOwner) {
            if (it.isSuccess) {
                showSnackBar("회원가입이 완료되었습니다. :)\n다시 로그인 해주세요.")
            } else {
                showSnackBar(it.exceptionOrNull()?.message ?: "회원가입에 실패했습니다. :(")
            }
        }

    }

    private fun showSignUpDialog() {
        AlertDialog.Builder(_activity)
            .setTitle("회원가입")
            .setMessage("존재하지 않는 회원 입니다.\n회원가입을 진행하시겠습니까?")
            .setPositiveButton("확인") { _, _ ->
                kakaologin { uId, name, email ->
                    loginViewModel.signUp(uId, name, email)
                }
            }
            .setNegativeButton("취소") { _, _ -> }
            .show()
    }

    /**
     * @param callback 카카오 로그인이 성공할 때만 동작합니다.
     */
    private fun kakaologin(callback: (uId: Long, name: String, email: String) -> (Unit)) {
        UserApiClient.instance.loginWithKakaoTalk(_activity) { token, error ->
            if (error != null) {
                Log.d(TAG, "kakaologin: ${error.message}")
                showSnackBar("카카오 로그인 실패")
            } else if (token != null) {
                UserApiClient.instance.me { user, error ->
                    if (error != null) {
                        Log.d(TAG, "kakaologin: ${error.message}")
                        showSnackBar("카카오 회원정보 호출 실패")
                    } else if (user != null) {
                        val uId = user.id
                        val name = user.kakaoAccount?.profile?.nickname
                        val email = user.kakaoAccount?.email

                        if (uId != null && name != null && email != null) {
                            callback(uId, name, email)
                        }
                    }
                }
            }
        }
    }
}