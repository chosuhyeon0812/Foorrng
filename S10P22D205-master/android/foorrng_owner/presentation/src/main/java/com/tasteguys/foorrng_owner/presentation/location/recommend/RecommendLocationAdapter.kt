package com.tasteguys.foorrng_owner.presentation.location.recommend

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tasteguys.foorrng_owner.presentation.databinding.ItemRecommendLocationBinding
import com.tasteguys.foorrng_owner.presentation.model.location.RecommendLocation

class RecommendLocationAdapter(
    private val recommendLocationList: List<RecommendLocation>,
    private val itemClicklListener: (RecommendLocation) -> Unit,
    private val navClickListener: (RecommendLocation) -> Unit,
    private val addClickListener: (RecommendLocation) -> Unit
) : RecyclerView.Adapter<RecommendLocationAdapter.RecommendLocationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendLocationViewHolder {
        return RecommendLocationViewHolder(
            ItemRecommendLocationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecommendLocationViewHolder, position: Int) {
        holder.bind(
            recommendLocationList[position],
            itemClicklListener,
            navClickListener,
            addClickListener
        )
    }

    override fun getItemCount(): Int {
        return recommendLocationList.size
    }

    class RecommendLocationViewHolder(
        private val binding: ItemRecommendLocationBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: RecommendLocation,
            itemClicklListener: (RecommendLocation) -> Unit,
            navClickListener: (RecommendLocation) -> Unit,
            addClickListener: (RecommendLocation) -> Unit
        ) {
            var menuString = item.foodList.joinToString(", ") + "을(를) 팔면 좋을거 같아요 :)"
            if (menuString.contains("기타")) menuString = "여기서 팔면 좋을거 같아요 :)"
            binding.tvRecommendLocationComment.text = menuString
            binding.tvRecommendLocationAddress.text = "대구광역시 "+item.address
            binding.layoutRecommendLocationItem.setOnClickListener {
                itemClicklListener(item)
            }
            binding.btnRecommendLocationNav.setOnClickListener {
                navClickListener(item)
            }
            binding.btnRecommendLocationAdd.setOnClickListener {
                addClickListener(item)
            }
        }
    }
}