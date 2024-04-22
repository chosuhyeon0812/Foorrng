package com.tasteguys.foorrng_customer.presentation.truck.info.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.tasteguys.foorrng_customer.presentation.base.BaseAdapter
import com.tasteguys.foorrng_customer.presentation.base.BaseHolder
import com.tasteguys.foorrng_customer.presentation.databinding.ItemTruckReviewBinding
import com.tasteguys.foorrng_customer.presentation.model.Review
import com.tasteguys.foorrng_customer.presentation.model.mapper.ReviewMap

private const val TAG = "TruckReviewAdapter"
class TruckReviewAdapter : BaseAdapter<Review>() {

    class ReviewHolder(val binding: ItemTruckReviewBinding) : BaseHolder<Review>(binding){
        @SuppressLint("SetTextI18n")
        override fun bindInfo(data: Review) {
            with(binding){
                Glide.with(root)
                    .load(ReviewMap.reviewMap[data.name])
                    .into(ivReviewIcon)
                tvReviewText.text = data.name
                tvCount.text = "${data.count}개"
                pbReview.apply {
                    max = data.maxCount
                    progress = data.count
                }
            }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<Review> {
        return ReviewHolder(ItemTruckReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
}