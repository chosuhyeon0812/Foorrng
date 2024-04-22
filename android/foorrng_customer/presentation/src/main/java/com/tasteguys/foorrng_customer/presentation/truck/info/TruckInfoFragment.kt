package com.tasteguys.foorrng_customer.presentation.truck.info

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayout
import com.tasteguys.foorrng_customer.presentation.R
import com.tasteguys.foorrng_customer.presentation.base.BaseFragment
import com.tasteguys.foorrng_customer.presentation.databinding.FragmentTruckInfoBinding
import com.tasteguys.foorrng_customer.presentation.main.MainBaseFragment
import com.tasteguys.foorrng_customer.presentation.main.MainToolbarControl
import com.tasteguys.foorrng_customer.presentation.main.MainViewModel
import com.tasteguys.foorrng_customer.presentation.truck.TruckViewModel
import com.tasteguys.foorrng_customer.presentation.truck.regist.RegisterTruckFragment
import com.tasteguys.retrofit_adapter.FoorrngException
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "TruckInfoFragment"

@AndroidEntryPoint
class TruckInfoFragment @Inject constructor(
    private val truckId: Long,
    private val truckName: String,
) : MainBaseFragment<FragmentTruckInfoBinding>(
    { FragmentTruckInfoBinding.bind(it) }, R.layout.fragment_truck_info
) {
    private val truckViewModel: TruckViewModel by viewModels()

    override fun setToolbar() {
        MainToolbarControl(
            visible = true,
            title = ""
        ).also {
            val data = truckViewModel.truckDetailResult.value?.getOrNull()
            changeToolbarName(data?.mainData?.name ?: "", data?.type ?: "")
        }
    }

    private fun changeToolbarName(name: String, type: String) {
        mainViewModel.changeToolbar(
            MainToolbarControl(
                true, name, menuRes = if (type == "foodtruck") 0 else R.menu.menu_edit,
                menuList = if(type == "foodtruck") listOf() else listOf(R.menu.menu_delete)
            ).also {
                mainViewModel.changeToolbar(it)
            }.addNavIconClickListener {
                parentFragmentManager.popBackStack()
            }.addMenuItemClickListener {
                if(it.title.toString()=="수정"){
                    parentFragmentManager.beginTransaction()
                        .replace(
                            R.id.fcv_container,
                            RegisterTruckFragment(false, truckId, truckViewModel)
                        )
                        .addToBackStack(null)
                        .commit()
                }else{
                    checkDeleteDialog()
                }
            }
        )
    }

    private var tabs = listOf<BaseFragment<out ViewBinding>>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        tabs = listOf(
            TruckBasicInfoFragment(truckId, truckViewModel),
            TruckDetailFragment(truckId, truckViewModel),
            TruckReviewFragment(truckId, truckName, truckViewModel)
        )
        registerObserve()
    }

    private fun initView() {
        truckViewModel.getTruckDetail(truckId)

        childFragmentManager.beginTransaction()
            .replace(R.id.fcv_truck_container, TruckBasicInfoFragment(truckId, truckViewModel))
            .commit()

        binding.tblTruckInfo.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    childFragmentManager.beginTransaction()
                        .replace(R.id.fcv_truck_container, tabs[tab!!.position])
                        .commit()
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {}
                override fun onTabUnselected(tab: TabLayout.Tab?) {}
            }
        )
    }

    private fun registerObserve() {
        truckViewModel.truckDetailResult.observe(viewLifecycleOwner) {
            if (it.isSuccess) {
                val data = it.getOrNull()!!.mainData
                val truckType = it.getOrNull()!!.type
                val picture = data.picture
                Glide.with(requireContext())
                    .load(picture)
                    .error(R.drawable.bg_profile_photo)
                    .centerCrop()
                    .circleCrop()
                    .into(binding.civTruckImg)
                changeToolbarName(data.name, truckType)

            }
        }
        truckViewModel.deleteTruckResult.observe(viewLifecycleOwner) {
            hideLoading()
            if (it.isSuccess) {
                val message = it.getOrNull()!!
                showToast(message)
                if (message == "삭제 되었습니다") {
                    parentFragmentManager.popBackStack()
                }
            } else {
                it.exceptionOrNull()?.let { t ->
                    if (t is FoorrngException && t.code == FoorrngException.ALREADY_DELETED) {
                        showSnackBar(t.message)
                    } else {
                        showSnackBar("오류")
                    }
                }
            }

        }

    }

    private fun checkDeleteDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("삭제 요청")
            .setMessage("삭제를 요청하시겠습니까?")
            .setPositiveButton(resources.getString(R.string.btn_confirm)) { _, _ ->
                showLoading()
                truckViewModel.deleteTruck(truckId)
            }
            .setNegativeButton(resources.getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

}