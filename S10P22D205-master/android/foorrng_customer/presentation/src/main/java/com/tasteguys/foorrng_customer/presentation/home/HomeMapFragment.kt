package com.tasteguys.foorrng_customer.presentation.home

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.util.MarkerIcons
import com.tasteguys.foorrng_customer.presentation.R
import com.tasteguys.foorrng_customer.presentation.base.BaseHolder
import com.tasteguys.foorrng_customer.presentation.databinding.FragmentHomeMapBinding
import com.tasteguys.foorrng_customer.presentation.main.MainBaseFragment
import com.tasteguys.foorrng_customer.presentation.model.MarkerWithData
import com.tasteguys.foorrng_customer.presentation.model.TruckDataWithAddress
import com.tasteguys.foorrng_customer.presentation.profile.UserViewModel
import com.tasteguys.foorrng_customer.presentation.profile.adapter.TruckAdapter
import com.tasteguys.foorrng_customer.presentation.truck.NavDialog
import com.tasteguys.foorrng_customer.presentation.truck.TruckViewModel
import com.tasteguys.foorrng_customer.presentation.truck.info.TruckInfoFragment
import com.tasteguys.foorrng_customer.presentation.truck.regist.RegisterTruckFragment
import com.tasteguys.foorrng_customer.presentation.truck.showNavDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.max
import kotlin.math.min

private const val TAG = "HomeMapFragment"

@AndroidEntryPoint
class HomeMapFragment : MainBaseFragment<FragmentHomeMapBinding>(
    { FragmentHomeMapBinding.bind(it) }, R.layout.fragment_home_map
) {

    private lateinit var mapView: MapView
    private lateinit var nMap: NaverMap
    private val truckViewModel: TruckViewModel by activityViewModels()
    private val homeMapViewModel: HomeMapViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()

    private val truckAdapter = TruckAdapter()

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAdapter()
    }


    private fun initView() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
        bottomSheetBehavior.maxHeight = 1200
        mapView = binding.mvMap
        mapView.getMapAsync {
            nMap = it.apply {
                locationSource = FusedLocationSource(
                    this@HomeMapFragment, 5000
                )
                uiSettings.isLocationButtonEnabled = true
                locationTrackingMode = LocationTrackingMode.Follow

                addOnCameraIdleListener {
                    binding.btnCurrentSearch.visibility = View.VISIBLE
                }

                setOnMapClickListener{ _, _ ->
                    with(homeMapViewModel){
                        if(selectedMarker!=null){
                            selectedMarker!!.icon = MarkerIcons.GREEN
                            selectedMarker!!.iconTintColor = Color.TRANSPARENT
                            selectedMarker = null
                        }
                    }
                }
            }
            registerObserve()
        }



        binding.btnCurrentSearch.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            val bound = nMap.contentBounds
            val nw = bound.northWest
            val se = bound.southEast
            val latMin = min(nw.latitude, se.latitude)
            val latMax = max(nw.latitude, se.latitude)
            val lngMin = min(nw.longitude, se.longitude)
            val lngMax = max(nw.longitude, se.longitude)

            truckViewModel.getTruckList(latMin, lngMin, latMax, lngMax)

            binding.btnCurrentSearch.visibility = View.GONE
        }

        binding.btnReport.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fcv_container, RegisterTruckFragment(true, -1, truckViewModel))
                .addToBackStack(null)
                .commit()
        }

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {}
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        //                        nMap.setContentPadding(0,0,0,1200, true)
                    }

                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        nMap.setContentPadding(0, 0, 0, bottomSheetBehavior.peekHeight, true)
                    }

                    BottomSheetBehavior.STATE_DRAGGING -> {}
                    BottomSheetBehavior.STATE_SETTLING -> {}
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {}
                }

            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                nMap.setContentPadding(
                    0,
                    0,
                    0,
                    (binding.bottomSheet.height * slideOffset).toInt(),
                    true
                )
            }
        }
        )

        binding.btnToggleVerified.setOnClickListener {
            it.isSelected = !it.isSelected
            homeMapViewModel.toggleOwnerAuthenticate()
            truckViewModel.ownerAuthenticated = !truckViewModel.ownerAuthenticated
        }

        binding.btnToggleIsOperating.setOnClickListener {
            it.isSelected = !it.isSelected
            homeMapViewModel.toggleOperating()
            truckViewModel.isOperating = !truckViewModel.isOperating
        }

        binding.btnToggleCategory.setOnClickListener {
            it.isSelected = !it.isSelected
            homeMapViewModel.toggleCategory()
            truckViewModel.isFavorite = !truckViewModel.isFavorite
        }
    }

    private fun initAdapter() {
        binding.rvTrucks.apply {
            adapter = truckAdapter.apply {
                setOnItemClickListener(object : BaseHolder.ItemClickListener {
                    override fun onClick(position: Int) {
                        val curTruck = currentList[position]
                        parentFragmentManager.beginTransaction()
                            .replace(
                                R.id.fcv_container,
                                TruckInfoFragment(curTruck.truckId, curTruck.name)
                            )
                            .addToBackStack(null)
                            .commit()
                    }
                })
                setOnButtonClickListener(object : TruckAdapter.TruckListHolder.ButtonClickListener {
                    override fun onToggleClick(position: Int) {
                        val curTruck = currentList[position]
                        truckViewModel.markFavoriteTruck(curTruck.truckId)
                    }

                    override fun onButtonClick(position: Int) {
                        val curTruck = currentList[position]
                        showNavDialog(requireContext(), curTruck.name, curTruck.lat, curTruck.lng)
                    }

                })
                layoutManager = LinearLayoutManager(context).apply {
                    orientation = LinearLayoutManager.VERTICAL
                }
            }
        }
    }


    private fun registerObserve() {
        truckViewModel.truckListResult.observe(viewLifecycleOwner) { res ->
            if (res.isSuccess) {
                with(binding){
                    btnToggleVerified.isSelected = truckViewModel.ownerAuthenticated
                    btnToggleIsOperating.isSelected = truckViewModel.isOperating
                    btnToggleCategory.isSelected = truckViewModel.isFavorite
                }
                with(homeMapViewModel) {
                    ownerAuthenticatedToggle = truckViewModel.ownerAuthenticated
                    operatingToggle = truckViewModel.isOperating
                    categoryToggle = truckViewModel.isFavorite
                    categoryList.clear()
                    originList.clear()
                    val data = res.getOrNull()!!
                    for (truck in data) {
                        val marker = Marker(LatLng(truck.lat, truck.lng)).apply {
                            setOnClickListener {
                                truckAdapter.submitList(listOf(truck))
                                if(selectedMarker!=null){
                                    selectedMarker!!.icon = MarkerIcons.GREEN
                                    selectedMarker!!.iconTintColor = Color.TRANSPARENT
                                }
                                selectedMarker = this
                                icon = MarkerIcons.BLACK
                                iconTintColor = resources.getColor(R.color.foorrng_orange)
                                true
                            }
                        }
                        originList.add(MarkerWithData(marker, truck))
                    }
                    userViewModel.getUserData()
//                    setTruckList()
                }
            }
        }

        with(userViewModel){
            getUserResult.observe(viewLifecycleOwner){
                if(it.isSuccess){
                    with(homeMapViewModel){
                        categoryList.addAll(it.getOrNull()!!.favoriteCategory)
                        setTruckList()
                    }
                }
            }
        }

        with(homeMapViewModel) {
            truckList.observe(viewLifecycleOwner) { it ->
                val lst = mutableListOf<TruckDataWithAddress>()
                for(marker in it){
                    lst.add(marker.data)
                    marker.marker.map = nMap
                }
                truckAdapter.submitList(lst)
                bottomSheetBehavior.peekHeight = if(lst.isNotEmpty()) 350 else 100
                nMap.setContentPadding(0,0,0, bottomSheetBehavior.peekHeight)
            }
        }
    }

}