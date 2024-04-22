package com.tasteguys.foorrng_owner.presentation.model.mapper

import com.naver.maps.geometry.LatLng
import com.tasteguys.foorrng_owner.domain.model.ArticleData
import com.tasteguys.foorrng_owner.domain.model.RecommendVillageData
import com.tasteguys.foorrng_owner.domain.model.foodtruck.FoodtruckInfoData
import com.tasteguys.foorrng_owner.domain.model.foodtruck.ReviewData
import com.tasteguys.foorrng_owner.domain.model.mark.MarkData
import com.tasteguys.foorrng_owner.domain.model.mark.OperationInfoData
import com.tasteguys.foorrng_owner.domain.model.menu.MenuData
import com.tasteguys.foorrng_owner.presentation.model.article.Article
import com.tasteguys.foorrng_owner.presentation.model.foodtruck.FoodTruckInfo
import com.tasteguys.foorrng_owner.presentation.model.foodtruck.Menu
import com.tasteguys.foorrng_owner.presentation.model.foodtruck.Review
import com.tasteguys.foorrng_owner.presentation.model.foodtruck.ReviewSet
import com.tasteguys.foorrng_owner.presentation.model.location.RecommendLocation
import com.tasteguys.foorrng_owner.presentation.model.location.RunInfo
import com.tasteguys.foorrng_owner.presentation.model.location.RunLocationInfo
import java.time.DayOfWeek

fun FoodtruckInfoData.toFoodtruckInfo(): FoodTruckInfo{
    return FoodTruckInfo(
        id = foodtruckId,
        name = name,
        carNumber = carNumber,
        callNumber = phoneNumber,
        category = category,
        notice = announcement,
        pictureUrl = picture,
        accountInfo = accountInfo,
        reviewSet = reviews.toReviewSet(totalReview),
        businessNumber = businessNumber
    )
}

fun List<ReviewData>.toReviewSet(totalReview: Int = -1): ReviewSet {
    return ReviewSet(
        totalCount = if(totalReview < 0) maxOf { it.count } else totalReview,
        reviewList = map {
            Review(it.id, it.count)
        }
    )
}

fun MenuData.toMenu(): Menu {
    return Menu(
        id,pictureUrl,name,price.toInt()
    )
}

fun MarkData.toRunLocationInfo(): RunLocationInfo{
    return RunLocationInfo(
        id,address,LatLng(latitude, longitude),isOpen,operationInfoList.map { it.toRunInfo() }
    )
}

fun OperationInfoData.toRunInfo(): RunInfo{
    return RunInfo(
        oneKoreanLetterTodayOfWeek(day),startTime,endTime
    )
}

fun oneKoreanLetterTodayOfWeek(string: String): DayOfWeek{
    return when(string){
        "월" -> DayOfWeek.MONDAY
        "화" -> DayOfWeek.TUESDAY
        "수" -> DayOfWeek.WEDNESDAY
        "목" -> DayOfWeek.THURSDAY
        "금" -> DayOfWeek.FRIDAY
        "토" -> DayOfWeek.SATURDAY
        "일" -> DayOfWeek.SUNDAY
        else -> throw IllegalArgumentException("Invalid DayOfWeek String")
    }
}

fun ArticleData.toArticle() = Article(
    articleId,userId,title,LatLng(latitude,longitude),phone,email,kakaoId,organizer,startDate,endDate,address,mainImage
)

fun RecommendVillageData.toRecommendLocation() : RecommendLocation {
    return RecommendLocation(
        address,foodList,area.map { LatLng(it.latitude, it.longitude) }
    )
}