package com.tasteguys.foorrng_customer.presentation.truck.regist

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import com.tasteguys.foorrng_customer.presentation.R
import com.tasteguys.foorrng_customer.presentation.base.GeoManager
import com.tasteguys.foorrng_customer.presentation.databinding.FragmentSelectLocationBinding
import com.tasteguys.foorrng_customer.presentation.festival.FestivalViewModel
import com.tasteguys.foorrng_customer.presentation.main.MainBaseFragment
import com.tasteguys.foorrng_customer.presentation.main.MainToolbarControl
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "TruckSelectLocationFrag"
@AndroidEntryPoint
class TruckSelectLocationFragment @Inject constructor(
    private val isNew:Boolean,
    private val registerInputViewModel: RegisterInputViewModel
) : MainBaseFragment<FragmentSelectLocationBinding>(
    { FragmentSelectLocationBinding.bind(it) }, R.layout.fragment_select_location
) {

    @Inject
    lateinit var geoManager: GeoManager

    private val selectMarkLocationViewModel: SelectLocationViewModel by viewModels()

    private lateinit var mapView: MapView
    private lateinit var nMap: NaverMap
    private val marker = Marker()

    override fun setToolbar() {
        MainToolbarControl(
            true, resources.getString(R.string.select_location)
        ).also {
            mainViewModel.changeToolbar(it)
        }.addNavIconClickListener {
            selectMarkLocationViewModel.setMark("", -1.0, -1.0)
            parentFragmentManager.popBackStack()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        registerObserver()
    }

    private fun initView() {
        mapView = binding.mvMap
        with(registerInputViewModel){
            if(markAddress.value!!.isNotEmpty()){
                marker.position = (LatLng(markLat.value!!, markLng.value!!))
                selectMarkLocationViewModel.setMark(
                    markAddress.value!!,
                    markLat.value!!,
                    markLng.value!!
                )
            }
        }


        binding.btnConfirm.setOnClickListener {
            with(selectMarkLocationViewModel){
                if(markAddress.value!!.isNotEmpty()){
                    registerInputViewModel.setMark(
                        markAddress.value!!,
                        markLat.value!!,
                        markLng.value!!
                    )
                }
            }

            parentFragmentManager.popBackStack()
        }


        mapView.getMapAsync {
            nMap = it.apply {
                locationSource = FusedLocationSource(
                    this@TruckSelectLocationFragment, 5000
                )
                uiSettings.isLocationButtonEnabled = true
                locationTrackingMode = LocationTrackingMode.Follow

                registerInputViewModel.markInfo.observe(viewLifecycleOwner) { res ->
                    if(res.address.isNotEmpty()){
                        marker.position = LatLng(res.lat, res.lng)
                        marker.map = this
                    }
                }

                setOnMapClickListener { _, coord ->
                    showLoading()
                    marker.position = LatLng(coord.latitude, coord.longitude)
                    marker.map = this
                    geoManager.getAddress(coord.latitude, coord.longitude) { address ->
                        selectMarkLocationViewModel.setMark(address, coord.latitude, coord.longitude)
                    }
                }
            }
        }
    }

    private fun registerObserver(){
        selectMarkLocationViewModel.markAddress.observe(viewLifecycleOwner){
            hideLoading()
            binding.tvAddress.text = it
        }
    }

}