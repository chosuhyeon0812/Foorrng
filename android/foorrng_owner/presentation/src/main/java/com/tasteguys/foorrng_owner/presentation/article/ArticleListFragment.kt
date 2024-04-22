package com.tasteguys.foorrng_owner.presentation.article

import android.os.Bundle
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.viewModels
import com.tasteguys.foorrng_owner.presentation.R
import com.tasteguys.foorrng_owner.presentation.databinding.FragmentArticleListBinding
import com.tasteguys.foorrng_owner.presentation.main.MainBaseFragment
import com.tasteguys.foorrng_owner.presentation.model.article.Article
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleListFragment : MainBaseFragment<FragmentArticleListBinding>(
    FragmentArticleListBinding::bind, R.layout.fragment_article_list
) {
    private val articleListViewModel: ArticleListViewModel by viewModels()

    private var articleListAdapter: ArticleListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        articleListViewModel.getArticleList() // UI 동작이상시 onCreateView로 이동
        registerObserve()
        registerListener()
    }

    private fun registerListener(){
        binding.ivSearch.setOnClickListener {
            articleListViewModel.articleListResult.value?.onSuccess { list ->
                list.filter {
                    val string = binding.etSearch.text.toString()
                    it.title.contains(string)||it.address.contains(string)
                }.let {
                    articleListAdapter?.submitList(it)
                }
            }
        }

        binding.ivBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun registerObserve() {
        articleListViewModel.articleListResult.observe(viewLifecycleOwner) { result ->
            result.onSuccess {
                setAdapter(it)
            }.onFailure {
                showToast(it.message.toString())
            }
        }


        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: android.text.Editable?) {
                if (s.isNullOrBlank()){
                    articleListViewModel.articleListResult.value?.onSuccess {
                        articleListAdapter?.submitList(it)
                    }
                }

            }
        })
    }

    private fun setAdapter(list: List<Article>) {
        articleListAdapter = ArticleListAdapter(articleItemClickListener)
        binding.rvArticleList.adapter = articleListAdapter?.apply {
            submitList(list)
        }
    }

    private val articleItemClickListener: (Article) -> Unit = {
        parentFragmentManager.beginTransaction()
            .replace(R.id.layout_main_fragment, ArticleDetailFragment(it))
            .addToBackStack(null)
            .commit()
    }
}