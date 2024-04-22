package com.tasteguys.foorrng_owner.presentation.location.open

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.tasteguys.foorrng_owner.presentation.R
import com.tasteguys.foorrng_owner.presentation.databinding.FragmentFoodtruckOpenBinding
import com.tasteguys.foorrng_owner.presentation.main.MainBaseFragment
import com.tasteguys.foorrng_owner.presentation.main.MainToolbarControl
import com.tasteguys.foorrng_owner.presentation.model.location.RunLocationInfo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodtruckOpenFragment : MainBaseFragment<FragmentFoodtruckOpenBinding>(
    FragmentFoodtruckOpenBinding::bind,R.layout.fragment_foodtruck_open
) {
    private val openLocationViewModel: FoodtruckOpenViewModel by viewModels()

    private var naverMap: NaverMap? = null
    private var markOverlayList = mutableListOf<Marker>()

    private var openLocationAdapter: OpenLocationAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity.onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            parentFragmentManager.popBackStack()
        }

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.layout_open_map) as MapFragment?
                ?: MapFragment.newInstance().also {
                    childFragmentManager.beginTransaction().add(R.id.layout_location_map, it)
                        .commit()
                }
        mapFragment.getMapAsync(mapCallback)
    }

    private fun init(){
        registerObserve()

        openLocationViewModel.getRunLocationInfoList().also {
            showLoading()
        }
    }

    private fun registerObserve(){
        openLocationViewModel.runLocationInfoListResult.observe(viewLifecycleOwner){ result ->
            hideLoading()
            result.onSuccess { list ->
                setAdapter(list)
                naverMap?.let { naverMap ->
                    naverMap.moveCamera(
                        CameraUpdate.fitBounds(
                            LatLngBounds.from(list.map { runLocationInfo -> runLocationInfo.latLng })
                            ,50
                        )
                    )
                }

                list.forEach { info ->
                    val marker = Marker().apply {
                        position = info.latLng
                        icon = OverlayImage.fromResource(R.drawable.ic_marker)
                        this.map = naverMap
                    }
                    markOverlayList.add(marker)
                }

                // 이미 영업 중인 위치가 있을 때
                list.find { it.isOpen }?.let {

                    showAlreadyExistOpenDialog(it)
                }
            }.onFailure {
                showToast(it.message ?: "영업 위치를 불러오는데 실패했습니다.")
                parentFragmentManager.popBackStack()
            }
        }

        openLocationViewModel.changeMarkStateResult.observe(viewLifecycleOwner){ result ->
            hideLoading()
            result.onSuccess {
                showToast("영업이 상태 변경이 완료됐습니다.")
            }.onFailure {
                showToast(it.message ?: "영업 상태를 변경하는데 실패했습니다.")
            }
            parentFragmentManager.popBackStack()
        }
    }

    private val itemClickListener : (RunLocationInfo) -> Unit = { runLocationInfo ->
        naverMap?.let {
            it.moveCamera(
                CameraUpdate.scrollTo(runLocationInfo.latLng).finishCallback {
                    it.moveCamera(CameraUpdate.zoomTo(16.0))
                }
            )
        }
    }

    private val startClickListener : (RunLocationInfo) -> Unit = {
        openLocationViewModel.changeOpenStateRunLocationInfo(it.id).also {
            showLoading()
        }
    }

    private fun setAdapter(list: List<RunLocationInfo>){
        openLocationAdapter = OpenLocationAdapter(
            list,
            startClickListener,
            itemClickListener
        )
        binding.rvOpenInfo.adapter = openLocationAdapter
    }

    private val mapCallback = OnMapReadyCallback { naverMap ->
        init()

        this.naverMap = naverMap.apply {
            // 한반도 영역 제한
            extent = LatLngBounds(LatLng(31.43, 122.37), LatLng(44.35, 132.0))
            uiSettings.apply {
                isTiltGesturesEnabled = false
                isRotateGesturesEnabled = false
            }
        }
    }

    private fun showAlreadyExistOpenDialog(runLocationInfo: RunLocationInfo){
        MaterialAlertDialogBuilder(mainActivity)
            .setTitle("이미 영업 중인 위치가 있습니다.")
            .setMessage("${runLocationInfo.address}에서 이미 영업 중입니다.\n영업을 종료할까요?")
            .setNegativeButton("취소"){ dialog, _ ->
                dialog.cancel()
            }
            .setPositiveButton("영업 종료"){ dialog, _ ->
                openLocationViewModel.changeOpenStateRunLocationInfo(runLocationInfo.id).also {
                    showLoading()
                }
                dialog.dismiss()
            }
            .setOnCancelListener { parentFragmentManager.popBackStack() }
            .show()
    }

    override fun setToolbar() {
        mainViewModel.changeToolbar(
            MainToolbarControl(
                true,
                "영업 시작"
            ).addNavIconClickListener {
                parentFragmentManager.popBackStack()
            }
        )
    }
}