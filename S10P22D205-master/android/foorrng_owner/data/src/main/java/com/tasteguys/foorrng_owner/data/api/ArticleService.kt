package com.tasteguys.foorrng_owner.data.api

import com.tasteguys.foorrng_owner.data.model.DefaultResponse
import com.tasteguys.foorrng_owner.data.model.article.ArticleResponse
import retrofit2.http.GET

interface ArticleService {
    @GET("article/list")
    suspend fun getArticleList(): Result<DefaultResponse<List<ArticleResponse>>>
}