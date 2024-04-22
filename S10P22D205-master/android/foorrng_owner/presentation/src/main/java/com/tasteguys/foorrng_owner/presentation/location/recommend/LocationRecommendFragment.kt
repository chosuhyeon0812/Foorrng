package com.tasteguys.foorrng_owner.presentation.location.recommend

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.bottomsheet.BottomSheetBehavior
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
import com.tasteguys.foorrng_owner.presentation.databinding.FragmentLocationRecommendBinding
import com.tasteguys.foorrng_owner.presentation.location.NavDialog
import com.tasteguys.foorrng_owner.presentation.location.regist.LocationRegistFragment
import com.tasteguys.foorrng_owner.presentation.location.showNavDialog
import com.tasteguys.foorrng_owner.presentation.main.MainBaseFragment
import com.tasteguys.foorrng_owner.presentation.main.MainToolbarControl
import com.tasteguys.foorrng_owner.presentation.model.location.RecommendLocation
import com.tasteguys.foorrng_owner.presentation.model.location.RunLocationInfo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationRecommendFragment : MainBaseFragment<FragmentLocationRecommendBinding>(
    FragmentLocationRecommendBinding::bind, R.layout.fragment_location_recommend
) {
    private val locationRecommendViewModel: LocationRecommendViewModel by viewModels()

    private var naverMap: NaverMap? = null
    private var recommendPolygonList = mutableListOf<PolygonOverlay>()

    private var recommendLocationAdapter: RecommendLocationAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.layout_recommend_map) as MapFragment?
                ?: MapFragment.newInstance().also {
                    childFragmentManager.beginTransaction().add(R.id.layout_recommend_map, it)
                        .commit()
                }
        mapFragment.getMapAsync(mapCallback)

    }

    private fun registerObserve() {
        locationRecommendViewModel.recommendLocationList.observe(viewLifecycleOwner) {
            hideLoading()
            it.onSuccess { list ->
                setAdapter(list)
                setCircleOverlay(list)
                mainViewModel.foodtruckInfo.value?.let { event ->
                    event.peekContent().onSuccess {
                        showSnackBar("사장님의 메뉴를 팔기 좋은 곳을 찾았어요")
                    }
                }
            }.onFailure {
                showToast("추천 위치를 불러오는데 실패했습니다.")
            }
        }
    }

    /**
     * 네이버 맵 콜백 이후 호출
     */
    private fun init(){
        bottomSheetSetting()

        registerObserve()
        if (recommendPolygonList.isEmpty()){
            locationRecommendViewModel.getRecommendLocationList().also {
                showLoading()
            }
        }
    }


    // region rv Adapter
    private fun setAdapter(list: List<RecommendLocation>) {
        if (recommendLocationAdapter == null) {
            recommendLocationAdapter = RecommendLocationAdapter(
                list,itemClickListener, navClickListener, addClickListener
            )
        }

        binding.rvRecommendLocation.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        binding.rvRecommendLocation.adapter = recommendLocationAdapter
    }

    private val itemClickListener: (RecommendLocation) -> Unit = { recommendLocation ->
        naverMap?.let {
            it.moveCamera(
                CameraUpdate.fitBounds(LatLngBounds.from(recommendLocation.area))
                    .animate(CameraAnimation.Easing)
            )

            Log.d("poooo", "${recommendLocation.area}")
        }
    }

    private val navClickListener: (RecommendLocation) -> Unit = { recommendLocation ->
        val lat = recommendLocation.area.map { it.latitude }.let {
            it.max()+it.min()/2
        }
        val lng = recommendLocation.area.map { it.longitude }.let {
            it.max()+it.min()/2
        }
        showNavDialog(mainActivity, recommendLocation.address, lat, lng)
    }

    private val addClickListener: (RecommendLocation) -> Unit = { recommendLocation ->
        parentFragmentManager.beginTransaction()
            .replace(R.id.layout_main_fragment, LocationRegistFragment(recommendLocation))
            .addToBackStack(null)
            .commit()
    }
    // endregion rv Adapter

    private fun setCircleOverlay(list: List<RecommendLocation>){
        // 기존 Polygon 제거
        recommendPolygonList.forEach{
            it.map = null
        }
        // Polygon 추가
        list.forEachIndexed{ index, recommendLocation ->
                val polygon = PolygonOverlay().apply {
                    coords = recommendLocation.area.also {
                        Log.d("poooo", "setCircleOverlay: $it")
                    }
                    color = resources.getColor(categotySolidColorResource(recommendLocation.foodList[0]), null)
                    outlineColor = resources.getColor(categotyStrokeColorResource(recommendLocation.foodList[0]), null)
                    outlineWidth = 3

                }
                recommendPolygonList.add(polygon)
                recommendPolygonList.forEach {
                    it.map = naverMap
                }
        }

        naverMap?.moveCamera(
            CameraUpdate.fitBounds(LatLngBounds.from(list.flatMap { it.area }))
                .animate(CameraAnimation.Easing)
        )
    }

    private val mapCallback = OnMapReadyCallback { map ->
        init()

        naverMap = map.apply {
            // 한반도 영역 제한
            extent = LatLngBounds(LatLng(31.43, 122.37), LatLng(44.35, 132.0))
            uiSettings.apply {
                isTiltGesturesEnabled = false
            }

            if (recommendPolygonList.isEmpty()){
                moveCamera(
                    CameraUpdate.fitBounds(LatLngBounds(LatLng(31.43, 122.37), LatLng(44.35, 132.0)))
                )
            }
        }
    }


    override fun setToolbar() {
        mainViewModel.changeToolbar(
            MainToolbarControl(
                title = resources.getString(R.string.location_recommend_title),
                visible = true,
            ).addNavIconClickListener {
                parentFragmentManager.popBackStack()
            }
        )
    }

    private fun bottomSheetSetting() {
        binding.layoutBottomSheet.setOnClickListener { }
        // 바텀시트 제어
        BottomSheetBehavior.from(binding.layoutBottomSheet)
            .addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    when (newState) {
                        BottomSheetBehavior.STATE_COLLAPSED -> {
                            naverMap?.setContentPadding(0, 0, 0, 0)
                        }

                        BottomSheetBehavior.STATE_EXPANDED -> {
                            naverMap?.setContentPadding(0, 0, 0, binding.layoutBottomSheet.height)
                        }
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
            })

    }
}