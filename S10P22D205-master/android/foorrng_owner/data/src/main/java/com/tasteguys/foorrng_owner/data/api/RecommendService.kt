package com.tasteguys.foorrng_owner.data.api

import com.tasteguys.foorrng_owner.data.model.DefaultResponse
import com.tasteguys.foorrng_owner.data.model.recommend.RecommendResponse
import retrofit2.http.GET

interface RecommendService {
    @GET("recommend")
    suspend fun getRecommendList(): Result<DefaultResponse<List<RecommendResponse>>>
}