package com.tasteguys.foorrng_owner.presentation.article

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasteguys.foorrng_owner.domain.usecase.article.GetArticleListUseCase
import com.tasteguys.foorrng_owner.presentation.model.article.Article
import com.tasteguys.foorrng_owner.presentation.model.mapper.toArticle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleListViewModel @Inject constructor(
    private val getArticleListUseCase: GetArticleListUseCase
) : ViewModel() {
    private var _articleListResult = MutableLiveData<Result<List<Article>>>()
    val articleListResult
        get() = _articleListResult

    fun getArticleList() {
        viewModelScope.launch {
            getArticleListUseCase().let {
                _articleListResult.postValue(
                    it.map { list ->
                        list.map { it.toArticle() }
                    }
                )
            }
        }
    }
}