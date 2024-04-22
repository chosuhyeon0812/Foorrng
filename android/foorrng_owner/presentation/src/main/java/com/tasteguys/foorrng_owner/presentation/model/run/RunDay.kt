package com.tasteguys.foorrng_owner.presentation.model.run

import java.time.DayOfWeek

data class RunDay(
    val day: DayOfWeek,
    val startTime: String,
    val endTime: String
)
