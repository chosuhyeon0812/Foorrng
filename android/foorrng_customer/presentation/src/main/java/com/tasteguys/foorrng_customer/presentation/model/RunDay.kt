package com.tasteguys.foorrng_customer.presentation.model

import java.time.DayOfWeek

data class RunDay(
    val day: DayOfWeek,
    val startTime: String,
    val endTime: String
)

fun DayOfWeek.toKorean(): String {
    return when(this){
        DayOfWeek.MONDAY -> "월"
        DayOfWeek.TUESDAY -> "화"
        DayOfWeek.WEDNESDAY -> "수"
        DayOfWeek.THURSDAY -> "목"
        DayOfWeek.FRIDAY -> "금"
        DayOfWeek.SATURDAY -> "토"
        DayOfWeek.SUNDAY -> "일"
    }
}
