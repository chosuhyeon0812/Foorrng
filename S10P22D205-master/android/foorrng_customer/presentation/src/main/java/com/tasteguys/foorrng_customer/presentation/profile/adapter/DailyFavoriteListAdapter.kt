package com.tasteguys.foorrng_customer.presentation.profile.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.tasteguys.foorrng_customer.presentation.base.BaseAdapter
import com.tasteguys.foorrng_customer.presentation.base.BaseHolder
import com.tasteguys.foorrng_customer.presentation.databinding.ItemFavoriteCategoryBinding
import com.tasteguys.foorrng_customer.presentation.model.FavoriteCategory

private const val TAG = "DailyFavoriteListAdapte"

class DailyFavoriteListAdapter: BaseAdapter<FavoriteCategory>() {

    class FavoriteHolder(private val binding: ItemFavoriteCategoryBinding) : BaseHolder<FavoriteCategory>(binding) {
        override fun bindInfo(data: FavoriteCategory) {
            with(binding){
                chipFavoriteCategory.text = data.name
                chipFavoriteCategory.isChecked = data.favorite
                chipFavoriteCategory.setOnClickListener {
                    clickListener.onClick(layoutPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<FavoriteCategory> {
       return FavoriteHolder(ItemFavoriteCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)).apply {
           setOnItemClickListener(clickListener)
       }
    }
}