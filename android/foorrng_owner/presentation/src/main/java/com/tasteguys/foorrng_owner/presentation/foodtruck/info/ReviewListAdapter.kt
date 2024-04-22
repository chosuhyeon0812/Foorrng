package com.tasteguys.foorrng_owner.presentation.foodtruck.info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tasteguys.foorrng_owner.presentation.databinding.ItemReviewBinding
import com.tasteguys.foorrng_owner.presentation.model.foodtruck.Review
import com.tasteguys.foorrng_owner.presentation.model.foodtruck.ReviewSet

class ReviewListAdapter(
    private val review: ReviewSet
) : RecyclerView.Adapter<ReviewListAdapter.ReviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(review.totalCount,review.reviewList[position])
    }

    override fun getItemCount(): Int = review.reviewList.size

    class ReviewViewHolder(private val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(totalCount: Int, review: Review) {
            with(binding) {
                tvReviewTitle.text = reviewIdToString(review.name)
                tvReviewCount.text = review.count.toString()+"개"
                progressBar.progress = review.count
                progressBar.max = totalCount
             }
        }

        fun reviewIdToString(reviewId: String): String{
            return idToKoreanMap[reviewId]?:reviewId
        }

        private val idToKoreanMap = hashMapOf(
            "rvIsDelicious" to "음식이 맛있어요",
            "rvIsSpecial" to "특별한 메뉴가 있어요",
            "rvIsCheap" to "가성비가 좋아요",
            "rvIsFast" to "음식이 빨리 나와요",
            "rvIsClean" to "매장이 청결해요",
            "rvIsCool" to "푸드트럭이 멋져요",
            "rvIsKind" to "사장님이 친절해요"
        )



    }
}
