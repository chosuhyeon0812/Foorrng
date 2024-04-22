package com.tasteguys.foorrng_customer.presentation.model.mapper

import com.tasteguys.foorrng_customer.presentation.R

object ReviewMap{
    private const val DELICIOUS = "음식이 맛있어요"
    private const val SPECIAL = "특별한 메뉴가 있어요"
    private const val CHEAP = "가성비가 좋아요"
    private const val FAST = "음식이 빨리 나와요"
    private const val COOL = "푸드트럭이 멋져요"
    private const val CLEAN = "매장이 청결해요"
    private const val KIND = "사장님이 친절해요"

    val reviewMap = mapOf(
        DELICIOUS to R.drawable.img_delicious,
        SPECIAL to R.drawable.img_special,
        CHEAP to R.drawable.img_cheap,
        FAST to R.drawable.img_fast,
        COOL to R.drawable.img_cool,
        CLEAN to R.drawable.img_clean,
        KIND to R.drawable.img_kind
    )

    val domainReviewMap = mapOf(
        "rvIsDelicious" to DELICIOUS,
        "rvIsSpecial" to SPECIAL,
        "rvIsCheap" to CHEAP,
        "rvIsFast" to FAST,
        "rvIsClean" to CLEAN,
        "rvIsCool" to COOL,
        "rvIsKind" to KIND
    )

    val registerReviewMap = mapOf(
        DELICIOUS to "rvIsDelicious",
        SPECIAL to "rvIsSpecial",
        CHEAP to "rvIsCheap",
        FAST to "rvIsFast",
        CLEAN to "rvIsClean",
        COOL to "rvIsCool",
        KIND to "rvIsKind"
    )
}