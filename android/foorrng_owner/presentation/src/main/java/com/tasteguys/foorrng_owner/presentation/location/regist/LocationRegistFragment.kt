package com.tasteguys.foorrng_owner.presentation.location.regist

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.CircleOverlay
import com.naver.maps.map.overlay.PolygonOverlay
import com.tasteguys.foorrng_owner.presentation.R
import com.tasteguys.foorrng_owner.presentation.base.GeoManager
import com.tasteguys.foorrng_owner.presentation.base.LocationProviderController
import com.tasteguys.foorrng_owner.presentation.base.WeekDaySelectManager
import com.tasteguys.foorrng_owner.presentation.databinding.FragmentLocationRegistBinding
import com.tasteguys.foorrng_owner.presentation.location.AddRundayDialog
import com.tasteguys.foorrng_owner.presentation.location.recommend.categotySolidColorResource
import com.tasteguys.foorrng_owner.presentation.location.recommend.categotyStrokeColorResource
import com.tasteguys.foorrng_owner.presentation.main.MainBaseFragment
import com.tasteguys.foorrng_owner.presentation.main.MainToolbarControl
import com.tasteguys.foorrng_owner.presentation.model.location.RecommendLocation
import com.tasteguys.foorrng_owner.presentation.model.run.RunDay
import com.tasteguys.foorrng_owner.presentation.model.run.RunLocation
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.util.Locale
import javax.inject.Inject

private const val TAG = "LocationRegistFragment_poorrng"

@AndroidEntryPoint
class LocationRegistFragment(
    private val recommentLocation: RecommendLocation? = null
) : MainBaseFragment<FragmentLocationRegistBinding>(
    FragmentLocationRegistBinding::bind, R.layout.fragment_location_regist
) {
    private val locationRegistViewModel: LocationRegistViewModel by viewModels()

    private lateinit var locationProviderController: LocationProviderController
    private var naverMap: NaverMap? = null

    private lateinit var weekDaySelectManager: WeekDaySelectManager
    private var runLocationAdapter: RegistDayAdapter? = null

    @Inject
    lateinit var geoManager: GeoManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.layout_location_map) as MapFragment?
                ?: MapFragment.newInstance().also {
                    childFragmentManager.beginTransaction().add(R.id.layout_location_map, it)
                        .commit()
                }
        mapFragment.getMapAsync(mapCallback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locationProviderController = LocationProviderController(mainActivity, this)
        weekDaySelectManager = WeekDaySelectManager(binding.layoutSelectWeekday, dayClickListener)

        mainActivity.onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            checkBackStackDialog()
        }
    }

    private fun init() {
        registerListener()
        registerObserve()
        if (recommentLocation != null) {
            naverMap?.moveCamera(
                CameraUpdate.fitBounds(LatLngBounds.from(recommentLocation.area))
                    .animate(CameraAnimation.Easing)
            )
            val polygon = PolygonOverlay().apply {
                coords = recommentLocation.area
                color = resources.getColor(categotySolidColorResource(recommentLocation.foodList[0]), null)
                outlineColor = resources.getColor(categotyStrokeColorResource(recommentLocation.foodList[0]), null)
                outlineWidth = 3
                map = naverMap
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

    }

    private fun registerListener() {
        binding.btnRegist.setOnClickListener {
            locationRegistViewModel.registRunLocationInfo()
        }
    }

    private fun registerObserve() {
        locationRegistViewModel.runDayList.observe(viewLifecycleOwner) {
            it.map { it.day }.toSet().let { selectDay ->
                weekDaySelectManager.setSelectedDay(selectDay)
            }

            if (runLocationAdapter == null) {
                runLocationAdapter = RegistDayAdapter(deleteBtnClickListener)
            }
            if (binding.rvRuninfoRegist.adapter == null) {
                binding.rvRuninfoRegist.adapter = runLocationAdapter
            }
            runLocationAdapter?.submitList(it)
        }

        locationRegistViewModel.runLocation.observe(viewLifecycleOwner) {
            binding.tvAddress.text = it.address
        }

        locationRegistViewModel.registResult.observe(viewLifecycleOwner){ result ->
            result.onSuccess {
                showToast("등록이 완료되었습니다.")
                parentFragmentManager.popBackStack()
            }.onFailure {
                showToast(it.message ?: "등록에 실패했습니다.")
            }

        }
    }

    private val deleteBtnClickListener: (RunDay) -> Unit = {
        locationRegistViewModel.deleteRunDay(it)
    }

    private val dayClickListener: (DayOfWeek, Boolean) -> Unit = { dayOfWeek, isOn ->
        showAddRunDayDialog(dayOfWeek)
    }

    private fun showAddRunDayDialog(dayOfWeek: DayOfWeek) {
        val dateFormat = SimpleDateFormat("HH:mm", Locale.KOREA)
        AddRundayDialog(requireContext(), dayOfWeek)
            .setCancelListener { dialog ->
                dialog.dismiss()
            }
            .setCreateListener { dialog, runDay ->
                if (runDay.startTime > runDay.endTime) {
                    showToast("시작 시간이 종료 시간보다 빨라야 합니다.")
                } else {
                    locationRegistViewModel.addRunDay(runDay)
                    dialog.dismiss()
                }
            }
            .show()
    }

    private fun checkBackStackDialog() {
        MaterialAlertDialogBuilder(_activity)
            .setTitle("등록을 취소하시겠습니까?")
            .setMessage("작성 중인 내용이 모두 지워집니다.")
            .setPositiveButton("확인") { _, _ ->
                parentFragmentManager.popBackStack()
            }
            .setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private val mapCallback = OnMapReadyCallback { naverMap ->
        this.naverMap = naverMap
        init()

        this.naverMap = naverMap.apply {
            // 한반도 영역 제한
            extent = LatLngBounds(LatLng(31.43, 122.37), LatLng(44.35, 132.0))
            uiSettings.apply {
                isTiltGesturesEnabled = false
            }

            addOnCameraIdleListener {
                geoManager.getAddress(
                    cameraPosition.target.latitude,
                    cameraPosition.target.longitude
                ) {
                    locationRegistViewModel.setRunLocation(
                        RunLocation(
                            it.replace("대한민국", ""), cameraPosition.target
                        )
                    )
                }
            }
        }
    }

    override fun setToolbar() {
        mainViewModel.changeToolbar(
            MainToolbarControl(
                true,
                "영업 위치 등록",
            ).addNavIconClickListener {
                checkBackStackDialog()
            }
        )
    }

}