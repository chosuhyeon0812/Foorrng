package com.tasteguys.foorrng_customer.data.mapper

import android.util.Log
import com.tasteguys.foorrng_customer.data.model.truck.mark.TruckOperationInfo
import com.tasteguys.foorrng_customer.data.model.truck.mark.TruckOperationRequest
import com.tasteguys.foorrng_customer.domain.model.truck.TruckOperationData
import java.text.SimpleDateFormat
import java.util.Locale

private const val TAG = "DomainToData"

fun TruckOperationData.toData() = TruckOperationInfo(
    day, startTime, endTime
)

fun TruckOperationData.toRequestData(): TruckOperationRequest{
    val dateFormat = SimpleDateFormat("HH:mm", Locale.KOREA)
    Log.d(TAG, "toRequestData: $startTime $endTime")
    return TruckOperationRequest(
        day, dateFormat.parse(startTime).time, dateFormat.parse(endTime).time
    )
}