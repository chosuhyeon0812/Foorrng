package com.tasteguys.foorrng_owner.data.mapper

import android.util.Log
import com.tasteguys.foorrng_owner.data.model.article.ArticleResponse
import com.tasteguys.foorrng_owner.data.model.foodtruck.FoodtruckDetailResponse
import com.tasteguys.foorrng_owner.data.model.foodtruck.ReviewResponse
import com.tasteguys.foorrng_owner.data.model.mark.MarkResponse
import com.tasteguys.foorrng_owner.data.model.mark.OperationInfoResponse
import com.tasteguys.foorrng_owner.data.model.menu.MenuResponse
import com.tasteguys.foorrng_owner.data.model.recommend.RecommendResponse
import com.tasteguys.foorrng_owner.data.model.user.LoginResponse
import com.tasteguys.foorrng_owner.domain.model.ArticleData
import com.tasteguys.foorrng_owner.domain.model.CoordinateData
import com.tasteguys.foorrng_owner.domain.model.RecommendVillageData
import com.tasteguys.foorrng_owner.domain.model.foodtruck.FoodtruckInfoData
import com.tasteguys.foorrng_owner.domain.model.foodtruck.ReviewData
import com.tasteguys.foorrng_owner.domain.model.mark.MarkData
import com.tasteguys.foorrng_owner.domain.model.mark.OperationInfoData
import com.tasteguys.foorrng_owner.domain.model.menu.MenuData
import com.tasteguys.foorrng_owner.domain.model.user.LoginData

fun LoginResponse.toDomain() = LoginData(
    isBusiRegist = isBusiRegist,
    accessToken = accessToken,
    refreshToken = refreshToken
)

fun FoodtruckDetailResponse.toFoodtruckInfoData() = FoodtruckInfoData(
    foodtruckId = foodtruck.foodtruckId,
    announcement = foodtruck.announcement,
    createdDay = foodtruck.createdDay,
    name = foodtruck.name,
    accountInfo = foodtruck.accountInfo,
    carNumber = foodtruck.carNumber,
    phoneNumber = foodtruck.phoneNumber,
    category = foodtruck.category,
    picture = foodtruck.picture,
    totalReview = totalReview,
    reviews = review.map { it.toReviewData() },
    businessNumber = foodtruck.businessNumber ?: ""
)

fun ReviewResponse.toReviewData() = ReviewData(
    id,count.toInt()
)

fun MenuResponse.toMenuData() = MenuData(
    id = id,
    name = name,
    price = price,
    pictureUrl = pictureUrl,
    foodtruckId = foodtruckId
)

fun MarkResponse.toMarkData() = MarkData(
    id, latitude, longitude, address, isOpen, operationInfoList.map { it.toOperationInfoData() }
)

fun OperationInfoResponse.toOperationInfoData() = OperationInfoData(
    day, startTime, endTime
)

fun ArticleResponse.toArticleData() = ArticleData(
    articleId = articleId,
    userId = userId,
    title = title,
    latitude = latitude,
    longitude = longitude,
    phone = phone,
    email = email,
    kakaoId = kakaoId,
    organizer = organizer,
    startDate = startDate,
    endDate = endDate,
    address = address,
    mainImage = mainImage
)

fun List<RecommendResponse>.toRecommendVillageList() : List<RecommendVillageData> {
    val villageFoodMap = HashMap<String, MutableList<String>>()
    val villageAreaMap = HashMap<String, MutableList<CoordinateData>>()


    this.forEach { recommendResponse ->
        recommendResponse.recommendList.forEach {
            if (it.area.size < 3) {
                return@forEach
            }
            if (villageFoodMap.containsKey(it.villageName)) {
                villageFoodMap.get(it.villageName)!!.add(recommendResponse.food)
            } else {
                villageFoodMap.put(it.villageName, mutableListOf(recommendResponse.food))
            }

            if (!villageAreaMap.containsKey(it.villageName)) {
                villageAreaMap.put(it.villageName, it.area.map { area -> CoordinateData(area.latitude, area.longitude) }.toMutableList())
            }
        }
    }

    return villageFoodMap.map { entry ->
        RecommendVillageData(entry.key, entry.value, villageAreaMap[entry.key]!!)
    }.also { Log.d(TAG, "toRecommendVillageList: $it") }
}

private const val TAG = "DataToDomain_poorrng"