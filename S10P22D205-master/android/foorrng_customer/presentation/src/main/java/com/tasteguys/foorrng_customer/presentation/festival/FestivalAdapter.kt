package com.tasteguys.foorrng_customer.presentation.festival

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.tasteguys.foorrng_customer.domain.model.festival.FestivalData
import com.tasteguys.foorrng_customer.presentation.R
import com.tasteguys.foorrng_customer.presentation.base.BaseAdapter
import com.tasteguys.foorrng_customer.presentation.base.BaseHolder
import com.tasteguys.foorrng_customer.presentation.base.toDateString
import com.tasteguys.foorrng_customer.presentation.databinding.ItemFestivalBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class FestivalAdapter: BaseAdapter<FestivalData>() {

    class FestivalHolder(private val binding: ItemFestivalBinding): BaseHolder<FestivalData>(binding){
        @SuppressLint("SetTextI18n")
        override fun bindInfo(data: FestivalData) {
            with(binding){
                tvTitle.text = data.title
                tvAddress.text = data.address
                tvDate.text = "${data.startDate.toDateString()} ~ ${data.endDate.toDateString()}"
                Glide.with(root.context)
                    .load(data.picture)
                    .fallback(R.drawable.bg_profile_photo)
                    .into(ivFestivalImg)
                root.setOnClickListener{
                    clickListener.onClick(layoutPosition)
                }
            }

        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<FestivalData> {
        return FestivalHolder(ItemFestivalBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            .apply { setOnItemClickListener(clickListener) }

    }
}