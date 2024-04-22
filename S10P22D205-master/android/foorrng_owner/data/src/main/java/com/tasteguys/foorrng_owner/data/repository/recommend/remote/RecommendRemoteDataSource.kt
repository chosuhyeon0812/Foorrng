package com.tasteguys.foorrng_owner.data.repository.recommend.remote

import com.tasteguys.foorrng_owner.data.model.DefaultResponse
import com.tasteguys.foorrng_owner.data.model.recommend.RecommendResponse

interface RecommendRemoteDataSource {
    suspend fun getRecommendList(): Result<List<RecommendResponse>>
}