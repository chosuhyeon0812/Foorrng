package com.tasteguys.foorrng_customer.presentation.login

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.gdd.presentation.base.PermissionHelper
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kakao.sdk.user.UserApiClient
import com.tasteguys.foorrng_customer.presentation.base.BaseFragment
import com.tasteguys.foorrng_customer.presentation.R
import com.tasteguys.foorrng_customer.presentation.base.PrefManager
import com.tasteguys.foorrng_customer.presentation.databinding.FragmentLoginBinding
import com.tasteguys.foorrng_customer.presentation.main.MainActivity
import com.tasteguys.retrofit_adapter.FoorrngException
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "LoginFragment"
@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>({FragmentLoginBinding.bind(it)}, R.layout.fragment_login) {

    @Inject
    lateinit var prefManager: PrefManager

    private val loginViewModel: LoginViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        checkPermission()
        setObserver()

    }

    private fun initView(){
        binding.buttonKakaoLogin.setOnClickListener {
            kakaologin{ uId, name, email ->
                loginViewModel.login(uId, name, email)
            }
        }
    }

    private fun setObserver(){
        loginViewModel.loginResult.observe(viewLifecycleOwner){
            if(it.isSuccess){
                if(!prefManager.getSurveyChecked()){
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fcv_container, DailyFavoriteFragment())
                        .commit()
                }else{
                    startActivity(Intent(requireContext(), MainActivity::class.java).apply{
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK + Intent.FLAG_ACTIVITY_NEW_TASK
                    })
                }
            }else{
                it.exceptionOrNull()?.let{t->
                    if(t is FoorrngException && t.code == FoorrngException.NOT_EXIST_USER){
                        showSignUpDialog()
                    }else{
                        showSnackBar("로그인에 실패했습니다. :(")
                    }
                }
            }
        }

        loginViewModel.signUpResult.observe(viewLifecycleOwner){
            if(it.isSuccess){
                showSnackBar("회원가입이 완료됐습니다. :)")
            }else{
                showSnackBar(it.exceptionOrNull()?.message?: "회원가입에 실패했습니다. :(")
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

    private fun kakaologin(callback: (uId: Long, name: String, email: String) -> (Unit)) {
        UserApiClient.instance.loginWithKakaoTalk(_activity) { token, error ->
            if (error != null) {
                showSnackBar("카카오 로그인 실패")
            } else if (token != null) {
                UserApiClient.instance.me { user, error ->
                    if (error != null) {
                        showSnackBar("카카오 회원정보 호출 실패")
                    } else if (user != null) {
                        with(user){
                            val uId = id
                            val name = kakaoAccount?.profile?.nickname
                            val email = kakaoAccount?.email
//                            Log.d(TAG, "kakaologin: uid: $uId, name: $name, email:$email ")
                            if (uId != null && name != null && email != null) {
                                callback(uId, name, email)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun checkPermission() {
        val permissionList =
            mutableListOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.CAMERA,
            ).apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    add(Manifest.permission.POST_NOTIFICATIONS)
                    add(Manifest.permission.READ_MEDIA_IMAGES)
                } else {
                    add(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }

        if (PermissionHelper.checkPermissionList(_activity, permissionList).isNotEmpty()) {
            PermissionHelper.requestPermissionList_fragment(this, permissionList.toTypedArray(),
                deniedListener = {
                    showPermissionDialog()
                })
        }
    }

    private fun showPermissionDialog() {
        MaterialAlertDialogBuilder(_activity)
            .setTitle("푸르릉과 함께하려면 다음의 권한이 필요해요")
            .setMessage("- 정확한 위치\n- 갤러리 접근\n- 카메라\n\n위의 권한이 없으면 많은 기능들을 이용하지 못합니다. 설정으로 이동할까요?")
            .setNegativeButton("취소") { _, _ -> }
            .setPositiveButton("확인") { _, _ ->
                startActivity(
                    Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:${_activity.packageName}")
                    ).apply {
                        addCategory(Intent.CATEGORY_DEFAULT)
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                )
            }
            .show()
    }

}