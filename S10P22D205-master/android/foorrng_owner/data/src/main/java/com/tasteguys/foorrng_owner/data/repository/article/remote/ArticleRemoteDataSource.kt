package com.tasteguys.foorrng_owner.data.repository.article.remote

import com.tasteguys.foorrng_owner.data.model.DefaultResponse
import com.tasteguys.foorrng_owner.data.model.article.ArticleResponse

interface ArticleRemoteDataSource {
    suspend fun getArticleList(): Result<List<ArticleResponse>>
}