package com.tasteguys.foorrng_owner.presentation.location.manage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.tasteguys.foorrng_owner.presentation.R
import com.tasteguys.foorrng_owner.presentation.base.LocationProviderController
import com.tasteguys.foorrng_owner.presentation.databinding.FragmentLocationManageBinding
import com.tasteguys.foorrng_owner.presentation.location.NavDialog
import com.tasteguys.foorrng_owner.presentation.location.regist.LocationRegistFragment
import com.tasteguys.foorrng_owner.presentation.location.showNavDialog
import com.tasteguys.foorrng_owner.presentation.main.MainBaseFragment
import com.tasteguys.foorrng_owner.presentation.main.MainToolbarControl
import com.tasteguys.foorrng_owner.presentation.model.location.RunLocationInfo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationManageFragment : MainBaseFragment<FragmentLocationManageBinding>(
    FragmentLocationManageBinding::bind, R.layout.fragment_location_manage
) {
    private val locationManageViewModel: LocationManageViewModel by viewModels()

    private var naverMap: NaverMap? = null
    private var markOverlayList = mutableListOf<Marker>()
    private lateinit var locationProviderController: LocationProviderController

    private var runLocationAdapter: RunLocationAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locationProviderController = LocationProviderController(mainActivity, this)
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.layout_location_map) as MapFragment?
                ?: MapFragment.newInstance().also {
                    childFragmentManager.beginTransaction().add(R.id.layout_location_map, it)
                        .commit()
                }
        mapFragment.getMapAsync(mapCallback)
    }

    /**
     * 네이버 맵 콜백 이후 호출
     */
    private fun init() {
        registerObserve()
        locationManageViewModel.getRunLocationInfoList()
    }

    private fun registerObserve() {
        locationManageViewModel.runLocationInfoListResult.observe(viewLifecycleOwner) { result ->
            hideLoading()
            result.onSuccess {
                setAdapter(it)
                if (!it.isEmpty()){
                    naverMap?.let { naverMap ->
                        naverMap.moveCamera(
                            CameraUpdate.fitBounds(
                                LatLngBounds.from(it.map { runLocationInfo -> runLocationInfo.latLng }),
                                50
                            )
                        )
                    }
                } else {
                    locationProviderController.getCurrnetLocation { task ->
                        hideLoading()
                        task.addOnSuccessListener {
                            naverMap?.moveCamera(
                                CameraUpdate.scrollTo(
                                    LatLng(it.latitude, it.longitude)
                                )
                            )
                        }
                    }.also {
                        showLoading()
                    }
                }
                it.forEach { info ->
                    val marker = Marker().apply {
                        position = info.latLng
                        icon = OverlayImage.fromResource(R.drawable.ic_marker)
                        this.map = naverMap
                    }
                    markOverlayList.add(marker)
                }
            }.onFailure {
                showToast(it.message ?: "영업 위치를 불러오는데 실패했습니다.")
            }
        }

        locationManageViewModel.deleteMarkResult.observe(viewLifecycleOwner){ result ->
            result.onSuccess {
                showToast("영업 위치를 삭제했습니다.")
                locationManageViewModel.getRunLocationInfoList()
            }.onFailure {
                showToast(it.message ?: "영업 위치 삭제에 실패했습니다.")
                hideLoading()
            }
        }
    }

    private fun setAdapter(runLocationList: List<RunLocationInfo>) {

        runLocationAdapter = RunLocationAdapter(
            runLocationList,
            deleteClickListener,
            naviClickListener,
            itemClickListener
        )

        binding.rvLocationInfo.adapter = runLocationAdapter
    }

    private val deleteClickListener: (RunLocationInfo) -> Unit = {
        showLoading()
        locationManageViewModel.deleteMark(it.id)
    }

    private val naviClickListener: (RunLocationInfo) -> Unit = {
        showNavDialog(
            mainActivity,
            it.address,
            it.latLng.latitude,
            it.latLng.longitude
        )
    }

    private val itemClickListener: (RunLocationInfo) -> Unit = { runLocationInfo ->
        naverMap?.let {
            it.moveCamera(
                CameraUpdate.scrollTo(runLocationInfo.latLng).finishCallback {
                    it.moveCamera(CameraUpdate.zoomTo(16.0))
                }
            )
        }
    }

    private val mapCallback = OnMapReadyCallback { naverMap ->
        init()

        this.naverMap = naverMap.apply {
            // 한반도 영역 제한
            extent = LatLngBounds(LatLng(31.43, 122.37), LatLng(44.35, 132.0))
            uiSettings.apply {
                isTiltGesturesEnabled = false
            }
        }
    }

    override fun setToolbar() {
        mainViewModel.changeToolbar(
            MainToolbarControl(
                title = resources.getString(R.string.location_manage_title),
                visible = true,
                menuRes = R.menu.menu_add
            ).addNavIconClickListener {
                parentFragmentManager.popBackStack()
            }.addMenuItemClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.layout_main_fragment, LocationRegistFragment())
                    .addToBackStack(null)
                    .commit()
            }
        )
    }
}