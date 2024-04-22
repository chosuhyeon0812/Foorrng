package com.tasteguys.foorrng_customer.presentation.festival


import android.annotation.SuppressLint
import android.graphics.Camera
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import com.tasteguys.foorrng_customer.presentation.R
import com.tasteguys.foorrng_customer.presentation.base.toDateString
import com.tasteguys.foorrng_customer.presentation.base.toFile
import com.tasteguys.foorrng_customer.presentation.databinding.FragmentFestivalDetailBinding
import com.tasteguys.foorrng_customer.presentation.festival.regist.FestivalInputViewModel
import com.tasteguys.foorrng_customer.presentation.festival.regist.RegisterFestivalFragment
import com.tasteguys.foorrng_customer.presentation.main.MainBaseFragment
import com.tasteguys.foorrng_customer.presentation.main.MainToolbarControl
import com.tasteguys.foorrng_customer.presentation.truck.regist.RegisterTruckFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FestivalDetailFragment @Inject constructor(
    private val id: Long
) : MainBaseFragment<FragmentFestivalDetailBinding>(
    { FragmentFestivalDetailBinding.bind(it)}, R.layout.fragment_festival_detail
) {

    private val festivalViewModel: FestivalViewModel by activityViewModels()
    private val festivalInputViewModel: FestivalInputViewModel by viewModels()
    private lateinit var mapView: MapView
    private lateinit var nMap: NaverMap

    override fun setToolbar() {
        MainToolbarControl(
            visible = true,
            title = ""
        ).also {
            changeToolbarName(festivalViewModel.getDetailResult.value?.getOrNull()?.title?:"")
        }
    }

    private fun changeToolbarName(name: String){
        mainViewModel.changeToolbar(
            MainToolbarControl(
                true, name, R.menu.menu_edit, menuList = listOf(R.menu.menu_delete)
            ).also {
                mainViewModel.changeToolbar(it)
            }.addNavIconClickListener {
                festivalViewModel.initAddress()
                parentFragmentManager.popBackStack()
            }.addMenuItemClickListener {
                if(it.title.toString()=="수정"){
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fcv_container, RegisterFestivalFragment(false, id))
                        .addToBackStack(null)
                        .commit()
                }else{
                    checkDeleteDialog()
                }

            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        registerObserve()
    }

    private fun initView(){
        mapView = binding.mvMap
        festivalViewModel.getFestivalDetail(id)
    }

    @SuppressLint("SetTextI18n")
    private fun registerObserve(){
        festivalViewModel.getDetailResult.observe(viewLifecycleOwner){
            if(it.isSuccess){
                val data = it.getOrNull()!!
                festivalViewModel.latLng = LatLng(data.lat, data.lng)
                with(binding){
                    tvOrganizer.text = data.organizer
                    tvEmail.text = data.email
                    tvKakao.text = data.kakao
                    tvPhoneNumber.text = data.phoneNumber
                    tvDate.text = "${data.startDate.toDateString()} ~ ${data.endDate.toDateString()}"
                    tvFestivalContent.text = data.content?:""
                    Glide.with(requireContext())
                        .load(data.picture)
                        .fallback(R.drawable.bg_profile_photo)
                        .into(ivFestivalInfo)

                    mapView.getMapAsync {
                        nMap = it.apply {
                            locationSource = FusedLocationSource(
                                this@FestivalDetailFragment, 5000
                            )
                            uiSettings.isLocationButtonEnabled = true
                            Marker().apply {
                                position = LatLng(data.lat, data.lng)
                                map = it
                            }
                            it.moveCamera(CameraUpdate.scrollTo(LatLng(data.lat, data.lng)))
                        }
                    }
                }
                changeToolbarName(data.title)
            }
        }
        festivalInputViewModel.deleteResult.observe(viewLifecycleOwner){
            if(it.isSuccess){
                festivalViewModel.initAddress()
                parentFragmentManager.popBackStack()
            }else{
                showToast("삭제 오류")
            }
        }
    }

    private fun checkDeleteDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("삭제 하시겠습니까?")
            .setMessage("삭제된 내용은 복구 할 수 없습니다.")
            .setPositiveButton(resources.getString(R.string.btn_confirm)) { _, _ ->
                festivalInputViewModel.deleteFestival(id)
            }
            .setNegativeButton(resources.getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

}