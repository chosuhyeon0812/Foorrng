package com.tasteguys.foorrng_customer.presentation.base

import android.content.Context
import android.location.Geocoder
import android.os.Build
import java.util.Locale

class GeoManager(private val context: Context) {
    fun getAddress( lat: Double, lng: Double, callback: (String)->Unit){
        val geoCoder = Geocoder(context, Locale.KOREAN)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            geoCoder.getFromLocation(
                lat, lng, 1
            ) { address ->
                if (address.size != 0) {
                    // 반환 값에서 전체 주소만 사용한다.
                    // getAddressLine(0)
                    callback(address[0].getAddressLine(0))
                }
            }
        } else { // API 레벨이 33 미만인 경우
            val addresses = geoCoder.getFromLocation(lat, lng, 1)
            if (addresses != null) {
                callback(addresses[0].getAddressLine(0))
            }
        }
    }
}