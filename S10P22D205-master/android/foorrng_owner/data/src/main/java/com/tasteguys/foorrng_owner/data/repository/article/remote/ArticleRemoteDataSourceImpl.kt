package com.tasteguys.foorrng_owner.data.repository.article.remote

import com.tasteguys.foorrng_owner.data.api.ArticleService
import com.tasteguys.foorrng_owner.data.mapper.toNonDefault
import com.tasteguys.foorrng_owner.data.model.article.ArticleResponse
import javax.inject.Inject

class ArticleRemoteDataSourceImpl @Inject constructor(
    private val articleService: ArticleService
): ArticleRemoteDataSource {
    override suspend fun getArticleList(): Result<List<ArticleResponse>> {
        return articleService.getArticleList()
            .toNonDefault()
    }
}