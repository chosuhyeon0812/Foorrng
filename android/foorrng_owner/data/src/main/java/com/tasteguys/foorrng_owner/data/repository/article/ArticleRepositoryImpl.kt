package com.tasteguys.foorrng_owner.data.repository.article

import com.tasteguys.foorrng_owner.data.mapper.toArticleData
import com.tasteguys.foorrng_owner.data.repository.article.remote.ArticleRemoteDataSource
import com.tasteguys.foorrng_owner.domain.model.ArticleData
import com.tasteguys.foorrng_owner.domain.repository.ArticleRepository
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val articleRemoteDataSource: ArticleRemoteDataSource
): ArticleRepository {
    override suspend fun getArticleList(): Result<List<ArticleData>> {
        return articleRemoteDataSource.getArticleList().map { list ->
            list.map { it.toArticleData() }
        }
    }
}