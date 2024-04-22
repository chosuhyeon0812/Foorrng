package com.tasteguys.foorrng_owner.domain.repository

import com.tasteguys.foorrng_owner.domain.model.RecommendVillageData

interface RecommendRepository {
    suspend fun getRecommendList(): Result<List<RecommendVillageData>>
}