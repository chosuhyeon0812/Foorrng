package com.tasteguys.foorrng_owner.data.repository.recommend

import com.tasteguys.foorrng_owner.data.mapper.toRecommendVillageList
import com.tasteguys.foorrng_owner.data.repository.recommend.remote.RecommendRemoteDataSource
import com.tasteguys.foorrng_owner.domain.model.RecommendVillageData
import com.tasteguys.foorrng_owner.domain.repository.RecommendRepository
import javax.inject.Inject

class RecommendRepositoryImpl @Inject constructor(
    private val recommendRemoteDataSource: RecommendRemoteDataSource
): RecommendRepository {
    override suspend fun getRecommendList(): Result<List<RecommendVillageData>> {
        return recommendRemoteDataSource.getRecommendList().map { it.toRecommendVillageList() }
    }
}