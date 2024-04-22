package com.tasteguys.foorrng_owner.presentation.location.recommend

import com.tasteguys.foorrng_owner.presentation.R

fun categotyStrokeColorResource(category: String): Int{
    return when(category){
        "덮밥" -> R.color.recommend_overlay_line_bowl
        "전기구이통닭"  -> R.color.recommend_overlay_line_chicken
        "꼬치" -> R.color.recommend_overlay_line_kkochi
        "타코야끼" -> R.color.recommend_overlay_line_takoyaki
        "타코 & 케밥" -> R.color.recommend_overlay_line_tako
        "분식" -> R.color.recommend_overlay_line_street
        "빵" -> R.color.recommend_overlay_line_bread
        "구황작물" -> R.color.recommend_overlay_line_potato
        "카페 & 디저트" -> R.color.recommend_overlay_line_cafe
        else -> R.color.recommend_overlay_line_etc
    }
}

fun categotySolidColorResource(category: String): Int{
    return when(category){
        "덮밥" -> R.color.recommend_overlay_solid_bowl
        "전기구이통닭"  -> R.color.recommend_overlay_solid_chicken
        "꼬치" -> R.color.recommend_overlay_solid_kkochi
        "타코야끼" -> R.color.recommend_overlay_solid_takoyaki
        "타코 & 케밥" -> R.color.recommend_overlay_solid_tako
        "분식" -> R.color.recommend_overlay_solid_street
        "빵" -> R.color.recommend_overlay_solid_bread
        "구황작물" -> R.color.recommend_overlay_solid_potato
        "카페 & 디저트" -> R.color.recommend_overlay_solid_cafe
        else -> R.color.recommend_overlay_solid_etc
    }
}