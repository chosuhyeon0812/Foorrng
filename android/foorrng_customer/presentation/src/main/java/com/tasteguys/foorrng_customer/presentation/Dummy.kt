package com.tasteguys.foorrng_customer.presentation

import com.tasteguys.foorrng_customer.presentation.model.FavoriteCategory
import com.tasteguys.foorrng_customer.presentation.model.Review
import com.tasteguys.foorrng_customer.presentation.model.TruckInfo
import com.tasteguys.foorrng_customer.presentation.model.TruckMark
import com.tasteguys.foorrng_customer.presentation.model.TruckMenu
import com.tasteguys.foorrng_customer.presentation.model.TruckOperationInfo
import com.tasteguys.foorrng_customer.presentation.model.TruckReview

fun main(){
    val a = mutableListOf(1, 2, 3)
    print(a.map{it->it*it})
    println(a)
}

object Dummy{

    val markInfo = mutableListOf(
        TruckMark(
            0, 0, 0, 0, "대구광역시 중구 명륜로 23길 80", mutableListOf(
                TruckOperationInfo("화", "12:00", "16:00"),
                TruckOperationInfo("금", "12:00", "16:00"),
            )

        ),
        TruckMark(
            0, 0, 0, 0, "대구광역시 중구 명륜로 24길", mutableListOf(
                TruckOperationInfo("월", "12:00", "16:00"),
                TruckOperationInfo("수", "12:00", "16:00"),
            )

        )

    )

}