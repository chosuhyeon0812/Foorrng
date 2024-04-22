package com.tasteguys.foorrng_customer.data.mapper

import com.tasteguys.foorrng_customer.data.model.FoodCategoryEntity
import com.tasteguys.foorrng_customer.data.model.festival.FestivalResponse
import com.tasteguys.foorrng_customer.data.model.truck.TruckDetailMarkResponse
import com.tasteguys.foorrng_customer.data.model.truck.TruckDetailMenuResponse
import com.tasteguys.foorrng_customer.data.model.truck.TruckDetailResponse
import com.tasteguys.foorrng_customer.data.model.truck.TruckFavoriteListResponse
import com.tasteguys.foorrng_customer.data.model.truck.TruckListResponse
import com.tasteguys.foorrng_customer.data.model.truck.TruckMainInfoResponse
import com.tasteguys.foorrng_customer.data.model.truck.menu.TruckMenuResponse
import com.tasteguys.foorrng_customer.data.model.truck.mark.TruckOperationInfo
import com.tasteguys.foorrng_customer.data.model.truck.mark.TruckRegisterOperationResponse
import com.tasteguys.foorrng_customer.data.model.truck.TruckRegisterUpdateResponse
import com.tasteguys.foorrng_customer.data.model.truck.review.TruckReviewResponse
import com.tasteguys.foorrng_customer.data.model.user.LoginResponse
import com.tasteguys.foorrng_customer.data.model.user.UserResponse
import com.tasteguys.foorrng_customer.domain.model.festival.FestivalData
import com.tasteguys.foorrng_customer.domain.model.food.FoodCategoryData
import com.tasteguys.foorrng_customer.domain.model.truck.FavoriteTruckData
import com.tasteguys.foorrng_customer.domain.model.truck.TruckData
import com.tasteguys.foorrng_customer.domain.model.truck.TruckDetailData
import com.tasteguys.foorrng_customer.domain.model.truck.TruckDetailMarkData
import com.tasteguys.foorrng_customer.domain.model.truck.TruckMainData
import com.tasteguys.foorrng_customer.domain.model.truck.TruckMenuData
import com.tasteguys.foorrng_customer.domain.model.truck.TruckOperationData
import com.tasteguys.foorrng_customer.domain.model.truck.TruckRegisterUpdateData
import com.tasteguys.foorrng_customer.domain.model.truck.TruckReviewData
import com.tasteguys.foorrng_customer.domain.model.user.TokenData
import com.tasteguys.foorrng_customer.domain.model.user.UserData

fun LoginResponse.toDomain() = TokenData(
    accessToken = accessToken,
    surveyChecked = surveyChecked
//    refreshToken = refreshToken
)

fun UserResponse.toDomain() = UserData(
    category, uid
)

fun TruckListResponse.toDomain() = TruckData(
    truckId = truckId,
    markId = markId,
    latitude = latitude,
    longitude = longitude,
    name = name,
    picture = picture,
    type = type,
    category = category,
    reviewCnt = reviewCnt,
    favorite = favorite,
    isOperating = isOperating
)

fun TruckDetailResponse.toDomain() = TruckDetailData(
    type,
    mainInfo.toDomain(),
    reviews.map { it.toDomain() },
    menus.map { it.toDomain(mainInfo.truckId)},
    totalReview,
    operation.map { it.toDomain() }
)

fun TruckMainInfoResponse.toDomain() = TruckMainData(
    announcement, name, createdDay, picture, accountInfo, carNumber, phoneNumber, bussiNumber?:"", category
)

fun TruckReviewResponse.toDomain() = TruckReviewData(
    id, cnt
)

fun TruckDetailMenuResponse.toDomain(truckId: Long) = TruckMenuData(
    id, name, price, picture, truckId
)

fun TruckMenuResponse.toDomain() = TruckMenuData(
    id, name, price, picture, truckId
)

fun TruckOperationInfo.toDomain() = TruckOperationData(
    day, startTime, endTime
)

fun TruckFavoriteListResponse.toDomain() = FavoriteTruckData(
    id, name, picture, category, reviewCnt
)

fun TruckRegisterUpdateResponse.toDomain() = TruckRegisterUpdateData(
    id, announcement, createdDay, picture, name, accountInfo, carNumber, phoneNumber, category
)

fun TruckDetailMarkResponse.toDomain() = TruckDetailMarkData(
    id, lat, lng, address, isOpen, operationInfoList.map { it.toDomain() }
)

fun TruckRegisterOperationResponse.toDomain() = TruckDetailMarkData(
    id, lat, lng, "", false, operationInfo.map { it.toDomain() }
)

fun FoodCategoryEntity.toDomain(): FoodCategoryData{
    return FoodCategoryData(name)
}

fun FestivalResponse.toDomain(): FestivalData{
    return FestivalData(
        id, title, lat, lng, phoneNumber, email, kakao, organizer, startDate, endDate, address, picture, content
    )
}

