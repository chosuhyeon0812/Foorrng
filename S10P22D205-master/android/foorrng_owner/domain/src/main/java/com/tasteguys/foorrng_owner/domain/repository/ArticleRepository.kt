package com.tasteguys.foorrng_owner.domain.repository

import com.tasteguys.foorrng_owner.domain.model.ArticleData

interface ArticleRepository {
    suspend fun getArticleList(): Result<List<ArticleData>>
}