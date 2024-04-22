package com.tasteguys.foorrng_customer.presentation.login

import android.os.Bundle
import android.view.View
import com.tasteguys.foorrng_customer.presentation.base.BaseActivity
import com.tasteguys.foorrng_customer.presentation.R
import com.tasteguys.foorrng_customer.presentation.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity: BaseActivity<ActivityLoginBinding>({ActivityLoginBinding.inflate(it)}) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction().replace(R.id.fcv_container, LoginFragment()).commit()

    }
}