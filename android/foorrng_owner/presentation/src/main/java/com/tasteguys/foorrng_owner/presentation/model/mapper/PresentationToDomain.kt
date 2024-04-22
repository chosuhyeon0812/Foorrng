package com.tasteguys.foorrng_owner.presentation.model.mapper

import com.tasteguys.foorrng_owner.domain.model.mark.MarkData
import com.tasteguys.foorrng_owner.domain.model.mark.OperationInfoData
import com.tasteguys.foorrng_owner.presentation.model.run.RunDay
import com.tasteguys.foorrng_owner.presentation.model.run.RunLocation

fun getMarkDataFromRunLocationRunDay(runDayList: List<RunDay>, runLocation: RunLocation): MarkData {
    return MarkData(
        0,
        runLocation.latLng.latitude,
        runLocation.latLng.longitude,
        runLocation.address,
        false,
        runDayList.map {
            OperationInfoData(
                it.day.toKorean(),
                it.startTime,
                it.endTime
            )
        }
    )
}