package com.tasteguys.foorrng_owner.data.mapper

import com.tasteguys.foorrng_owner.data.model.mark.MarkRegistDto
import com.tasteguys.foorrng_owner.data.model.mark.MarkRegistRequest
import com.tasteguys.foorrng_owner.data.model.mark.OperationInfoDto
import com.tasteguys.foorrng_owner.data.model.mark.OperationInfoRequest
import com.tasteguys.foorrng_owner.data.model.mark.OperationInfoResponse
import com.tasteguys.foorrng_owner.domain.model.mark.MarkData
import java.text.SimpleDateFormat
import java.util.Locale

fun MarkData.toMarkRegistRequest(): MarkRegistRequest{
    val dateFormat = SimpleDateFormat("HH:mm", Locale.KOREA)
    return MarkRegistRequest(
        MarkRegistDto(
            address,latitude,longitude
        ),
        OperationInfoDto(
            operationInfoList.map {
                OperationInfoRequest(
                    it.day,
                    dateFormat.parse(it.startTime).time,
                    dateFormat.parse(it.endTime).time
                )
            }
        )
    )
}