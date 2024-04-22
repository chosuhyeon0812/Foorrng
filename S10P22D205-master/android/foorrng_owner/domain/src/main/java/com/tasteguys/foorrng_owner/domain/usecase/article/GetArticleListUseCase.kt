package com.tasteguys.foorrng_owner.domain.usecase.article

import com.tasteguys.foorrng_owner.domain.repository.ArticleRepository
import javax.inject.Inject

class GetArticleListUseCase @Inject constructor(
    private val articleRepository: ArticleRepository
) {
    suspend operator fun invoke() = articleRepository.getArticleList()
}