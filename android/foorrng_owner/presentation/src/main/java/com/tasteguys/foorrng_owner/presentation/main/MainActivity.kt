package com.tasteguys.foorrng_owner.presentation.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.tasteguys.foorrng_owner.presentation.R
import com.tasteguys.foorrng_owner.presentation.base.BaseActivity
import com.tasteguys.foorrng_owner.presentation.busi_number.BusiNumberFragment
import com.tasteguys.foorrng_owner.presentation.databinding.ActivityMainBinding
import com.tasteguys.foorrng_owner.presentation.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "MainActivity_Genseong"

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(
    ActivityMainBinding::inflate
) {
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeToolbarControl()

        val isRegist = intent.getBooleanExtra("is_regist", false)

        supportFragmentManager.beginTransaction()
            .replace(
                R.id.layout_main_fragment,
                if (isRegist) HomeFragment() else BusiNumberFragment()
            )
            .commit()

    }

    private fun observeToolbarControl() {
        mainViewModel.toolbarController.observe(this) { contoller ->
            with(binding.tbMain) {
                if (contoller.visible) {
                    binding.tbMain.visibility = View.VISIBLE

                    title = contoller.title

                    menu.clear()
                    if (contoller.menuRes != 0){
                        inflateMenu(contoller.menuRes)
                        setOnMenuItemClickListener {
                            contoller.menuItemClickListener(it)
                            true
                        }
                    }

                    setNavigationOnClickListener {
                        contoller.navIconClickListener()
                    }
                } else {
                    binding.tbMain.visibility = View.GONE
                }
            }

            binding.root.invalidate()

        }
    }
}