package com.tasteguys.foorrng_customer.presentation.truck.info.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.tasteguys.foorrng_customer.presentation.base.BaseAdapter
import com.tasteguys.foorrng_customer.presentation.base.BaseHolder
import com.tasteguys.foorrng_customer.presentation.databinding.ItemReviewBtnBinding
import com.tasteguys.foorrng_customer.presentation.model.mapper.ReviewMap

class TruckReviewBtnAdapter: BaseAdapter<String>() {

    class ReviewBtnHolder(val binding: ItemReviewBtnBinding): BaseHolder<String>(binding){
        override fun bindInfo(data: String) {
            with(binding){
                Glide.with(root)
                    .load(ReviewMap.reviewMap[data])
                    .into(ivReviewIcon)
                tvReviewText.text = data
                root.setOnClickListener {
                    root.isSelected = !root.isSelected
                    clickListener.onClick(layoutPosition)
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<String> {
        return ReviewBtnHolder(ItemReviewBtnBinding.inflate(LayoutInflater.from(parent.context), parent, false)).apply{
            setOnItemClickListener(clickListener)
        }
    }
}