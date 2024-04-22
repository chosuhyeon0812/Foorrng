package com.tasteguys.foorrng_customer.presentation.truck.info

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import com.tasteguys.foorrng_customer.presentation.Dummy
import com.tasteguys.foorrng_customer.presentation.R
import com.tasteguys.foorrng_customer.presentation.base.BaseFragment
import com.tasteguys.foorrng_customer.presentation.databinding.FragmentTruckDetailBinding
import com.tasteguys.foorrng_customer.presentation.truck.TruckViewModel
import com.tasteguys.foorrng_customer.presentation.truck.info.adapter.TruckMarkInfoAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val TAG = "TruckDetailFragment"

@AndroidEntryPoint
class TruckDetailFragment @Inject constructor(
    truckId: Long,
    private val truckViewModel: TruckViewModel
) : BaseFragment<FragmentTruckDetailBinding>(
    { FragmentTruckDetailBinding.bind(it) }, R.layout.fragment_truck_detail
) {

    private lateinit var mapView: MapView
    private lateinit var nMap: NaverMap

    private val truckMarkInfoAdapter = TruckMarkInfoAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        registerObserve()

    }

    private fun initView() {
        mapView = binding.mvMap
        binding.rvOpInfo.apply {
            adapter = truckMarkInfoAdapter
        }
    }

    private fun registerObserve() {
        mapView.getMapAsync {
            nMap = it.apply {
                locationSource = FusedLocationSource(
                    this@TruckDetailFragment, 5000
                )
                uiSettings.isLocationButtonEnabled = true
                truckViewModel.truckDetailResult.observe(viewLifecycleOwner) { res ->
                    if (res.isSuccess) {
                        val marks = res.getOrNull()!!.operation
                        for (data in marks) {
                            Marker().apply {
                                position = LatLng(data.lat, data.lng)
                                map = it
                            }
                        }
                        it.moveCamera(CameraUpdate.scrollTo(LatLng(marks[0].lat, marks[0].lng)))
                        truckMarkInfoAdapter.submitList(marks)
                    }
                }
            }
        }

    }


}