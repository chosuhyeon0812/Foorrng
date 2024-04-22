package com.tasteguys.foorrng_owner.presentation.article

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tasteguys.foorrng_owner.presentation.databinding.ItemArticleBinding
import com.tasteguys.foorrng_owner.presentation.model.article.Article
import java.text.SimpleDateFormat
import java.util.Locale

class ArticleListAdapter(
    private val itemClickListener: (Article) -> Unit
) : ListAdapter<Article,ArticleListAdapter.ArticleViewHolder>(diffUtil)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            ItemArticleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(currentList[position], itemClickListener)
    }

    class ArticleViewHolder(
        private val binding: ItemArticleBinding
    ): RecyclerView.ViewHolder(binding.root){
        private val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)

        fun bind(item: Article, itemClickListener: (Article) -> Unit){
            binding.tvArticleListTitle.text = item.title
            binding.tvArticleListDate.text = "${dateFormat.format(item.startDate)} ~ ${dateFormat.format(item.endDate)}"
            binding.tvArticleListAddress.text = item.address
            Glide.with(binding.root)
                .load(item.mainImage)
                .into(binding.ivArticleListImage)
            binding.cvArticleListItem.setOnClickListener {
                itemClickListener(item)
            }
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<Article>(){
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.articleId == newItem.articleId
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }
}