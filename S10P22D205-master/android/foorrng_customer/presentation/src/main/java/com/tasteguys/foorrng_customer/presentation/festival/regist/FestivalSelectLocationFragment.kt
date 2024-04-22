package com.tasteguys.foorrng_customer.presentation.festival.regist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
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

private const val TAG = "FestivalSelectLocationF"
@AndroidEntryPoint
class FestivalSelectLocationFragment @Inject constructor(
) : MainBaseFragment<FragmentSelectLocationBinding>(
    { FragmentSelectLocationBinding.bind(it) }, R.layout.fragment_select_location
) {

    @Inject lateinit var geoManager: GeoManager

    private val festivalViewModel: FestivalViewModel by activityViewModels()

    private lateinit var mapView: MapView
    private lateinit var nMap: NaverMap
    private val marker = Marker()

    override fun setToolbar() {
        MainToolbarControl(
            true, resources.getString(R.string.select_location)
        ).also {
            mainViewModel.changeToolbar(it)
        }.addNavIconClickListener {
            festivalViewModel.setTempAddress("")
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

        binding.btnConfirm.setOnClickListener {
            festivalViewModel.setAddress(festivalViewModel.tempAddress.value!!)
            parentFragmentManager.popBackStack()
        }

        mapView.getMapAsync {
            nMap = it.apply {
                locationSource = FusedLocationSource(
                    this@FestivalSelectLocationFragment, 5000
                )
                uiSettings.isLocationButtonEnabled = true
                locationTrackingMode = LocationTrackingMode.Follow
                with(festivalViewModel){
                    if(address.value!!.isNotEmpty()){
                        marker.apply {
                            position = latLng
                            map = it
                        }
                        it.moveCamera(CameraUpdate.scrollTo(latLng))
                    }
                }

                setOnMapClickListener { _, coord ->
                    showLoading()
                    marker.position = LatLng(coord.latitude, coord.longitude)
                    marker.map = this
                    festivalViewModel.latLng = marker.position
                    geoManager.getAddress(coord.latitude, coord.longitude) { address ->
                        festivalViewModel.setTempAddress(address)
                    }
                }
            }
        }
    }

    private fun registerObserver(){
        festivalViewModel.tempAddress.observe(viewLifecycleOwner){
            hideLoading()
            binding.tvAddress.text = it
        }
    }

}