package com.tasteguys.foorrng_customer.presentation.truck.info.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import com.tasteguys.foorrng_customer.domain.model.truck.TruckOperationData
import com.tasteguys.foorrng_customer.presentation.base.BaseAdapter
import com.tasteguys.foorrng_customer.presentation.base.BaseHolder
import com.tasteguys.foorrng_customer.presentation.databinding.ItemOperationDailyDetailBinding

class TruckOperationInfoAdapter: BaseAdapter<TruckOperationData>() {

    class OperationInfoHolder(private val binding: ItemOperationDailyDetailBinding): BaseHolder<TruckOperationData>(binding){
        @SuppressLint("SetTextI18n")
        override fun bindInfo(data: TruckOperationData) {
            with(binding){
                tvDay.text = "${data.day}요일"
                tvTime.text="${data.startTime}~${data.endTime}"
            }
        }

    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseHolder<TruckOperationData> {
        return OperationInfoHolder(ItemOperationDailyDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
}