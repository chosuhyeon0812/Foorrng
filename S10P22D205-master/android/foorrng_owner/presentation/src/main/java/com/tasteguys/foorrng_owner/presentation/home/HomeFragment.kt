package com.tasteguys.foorrng_owner.presentation.home

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tasteguys.foorrng_owner.presentation.R
import com.tasteguys.foorrng_owner.presentation.article.ArticleListFragment
import com.tasteguys.foorrng_owner.presentation.base.PermissionHelper
import com.tasteguys.foorrng_owner.presentation.databinding.FragmentHomeBinding
import com.tasteguys.foorrng_owner.presentation.foodtruck.info.FoodtruckInfoFragment
import com.tasteguys.foorrng_owner.presentation.foodtruck.menu.FoodtruckMenuFragment
import com.tasteguys.foorrng_owner.presentation.foodtruck.menu.MenuEditFragment
import com.tasteguys.foorrng_owner.presentation.foodtruck.regist.RegistFoodtruckFragment
import com.tasteguys.foorrng_owner.presentation.location.manage.LocationManageFragment
import com.tasteguys.foorrng_owner.presentation.location.open.FoodtruckOpenFragment
import com.tasteguys.foorrng_owner.presentation.location.recommend.LocationRecommendFragment
import com.tasteguys.foorrng_owner.presentation.main.MainBaseFragment
import com.tasteguys.retrofit_adapter.FoorrngException
import java.time.DayOfWeek

class HomeFragment : MainBaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::bind, R.layout.fragment_home
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DayOfWeek.values().forEach {
            Log.d("dayofweek", "onViewCreated: $it ${it.value}")
        }

        mainActivity.onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            mainActivity.onBackPressedCallback
        )

        checkPermission()

        registerListener()
        registerObserve()

        mainViewModel.getFoodtruckInfo().also {
            showLoading()
        }
    }

    private fun registerObserve() {
        mainViewModel.foodtruckInfo.observe(viewLifecycleOwner) {
            hideLoading()
            it.getContentIfNotHandled()?.let { result ->
                result.onSuccess { foodTruckInfo ->
                    binding.tvFoodtruckName.text = foodTruckInfo.name
                    Glide.with(binding.root)
                        .load(foodTruckInfo.pictureUrl)
                        .fitCenter()
                        .circleCrop()
                        .into(binding.ivProfile)
                }.onFailure {
                    if (it is FoorrngException && it.code == "F-001"){
                        showNonFoodtruckDialog()
                    } else {
                        showToast(it.message ?: "네트워크 에러")
                    }
                }
            }
        }
    }

    private fun registerListener() {
        binding.cvMyFoodtruck.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.layout_main_fragment, FoodtruckInfoFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.cvManageMenu.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.layout_main_fragment, FoodtruckMenuFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.cvManageLocation.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.layout_main_fragment, LocationManageFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.cvRecommendLocation.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.layout_main_fragment, LocationRecommendFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.cvFoodtruckOpen.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.layout_main_fragment, FoodtruckOpenFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.cvArticle.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.layout_main_fragment, ArticleListFragment())
                .addToBackStack(null)
                .commit()
        }
    }


    private fun checkPermission() {
        val permissionList =
            mutableListOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CAMERA,
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

    private fun showNonFoodtruckDialog(){
        MaterialAlertDialogBuilder(mainActivity)
            .setTitle("푸드트럭을 등록해주세요")
            .setMessage("푸드트럭 정보가 없습니다. 앱을 사용하기 위해 푸드트럭을 등록해주세요")
            .setPositiveButton("등록하기") { _, _ ->
                parentFragmentManager.beginTransaction()
                    .replace(R.id.layout_main_fragment, RegistFoodtruckFragment())
                    .commit()
            }
            .setNegativeButton("취소"){dialog,_ ->
                dialog.cancel()
            }
            .setOnCancelListener { mainActivity.finish() }
            .show()
    }

}