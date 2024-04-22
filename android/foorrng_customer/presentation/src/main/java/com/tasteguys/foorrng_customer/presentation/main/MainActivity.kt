package com.tasteguys.foorrng_customer.presentation.main

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.tasteguys.foorrng_customer.presentation.R
import com.tasteguys.foorrng_customer.presentation.base.BaseActivity
import com.tasteguys.foorrng_customer.presentation.databinding.ActivityMainBinding
import com.tasteguys.foorrng_customer.presentation.festival.FestivalListFragment
import com.tasteguys.foorrng_customer.presentation.home.HomeMapFragment
import com.tasteguys.foorrng_customer.presentation.login.DailyFavoriteViewModel
import com.tasteguys.foorrng_customer.presentation.profile.UserFavoriteFragment
import com.tasteguys.foorrng_customer.presentation.truck.regist.RegisterTruckFragment
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "MainActivity_Genseong"
@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(
    ActivityMainBinding::inflate
) {
    private val mainViewModel: MainViewModel by viewModels()
    private val dailyFavoriteViewModel: DailyFavoriteViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dailyFavoriteViewModel.getCategory()
        observeToolbarControl()
        setBottomNavigationView()
        supportFragmentManager.beginTransaction().replace(R.id.fcv_container, HomeMapFragment()).commit()
    }

    private fun setBottomNavigationView(){
        binding.bnvBottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.fragment_home_map ->{
                    supportFragmentManager.beginTransaction().replace(R.id.fcv_container, HomeMapFragment()).commit()
                    true
                }
//                R.id.fragment_festival->{
//                    supportFragmentManager.beginTransaction().replace(R.id.fcv_container, FestivalListFragment()).commit()
//                    true
//                }
                R.id.fragment_favorite->{
                    supportFragmentManager.beginTransaction().replace(R.id.fcv_container, UserFavoriteFragment()).commit()
                    true
                }
                else -> false
            }
        }
    }


    private fun observeToolbarControl(){
        mainViewModel.toolbarController.observe(this){ controller ->
            with(binding.tbMain){
                if(controller.visible){
                    binding.tbMain.visibility = View.VISIBLE
                    title = controller.title
                    menu.clear()
                    if(controller.menuRes!=0){
                        inflateMenu(controller.menuRes)
                        for(id in controller.menuList){
                            inflateMenu(id)
                        }
                    }
                    setOnMenuItemClickListener {
                        controller.menuItemClickListener(it)
                        true
                    }
                    setNavigationOnClickListener {
                        controller.navIconClickListener()
                    }
                    navigationIcon = if(!controller.backIcon){
                        null
                    }else{
                        resources.getDrawable(R.drawable.ic_arrow_back)
                    }

                }
                else {
                    binding.tbMain.visibility = View.GONE
                }
            }

            binding.root.invalidate()

        }
    }
}