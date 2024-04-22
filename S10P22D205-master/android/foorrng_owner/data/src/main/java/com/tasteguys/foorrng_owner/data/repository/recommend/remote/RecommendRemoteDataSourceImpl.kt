package com.tasteguys.foorrng_owner.data.repository.recommend.remote

import com.tasteguys.foorrng_owner.data.api.RecommendService
import com.tasteguys.foorrng_owner.data.mapper.toNonDefault
import com.tasteguys.foorrng_owner.data.model.recommend.RecommendResponse
import javax.inject.Inject

class RecommendRemoteDataSourceImpl @Inject constructor(
    private val recommendService: RecommendService
) : RecommendRemoteDataSource{
    override suspend fun getRecommendList(): Result<List<RecommendResponse>> {
        return recommendService.getRecommendList().toNonDefault()
    }
}