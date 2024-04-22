package com.tasteguys.foorrng_customer.presentation.truck.regist

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tasteguys.foorrng_customer.presentation.base.BaseAdapter
import com.tasteguys.foorrng_customer.presentation.base.BaseHolder
import com.tasteguys.foorrng_customer.presentation.databinding.ItemRundayBinding
import com.tasteguys.foorrng_customer.presentation.model.RunDay
import com.tasteguys.foorrng_customer.presentation.model.toKorean

class RunDayAdapter: BaseAdapter<RunDay> () {

    class RunDayInfoHolder(private val binding: ItemRundayBinding): BaseHolder<RunDay>(binding){
        override fun bindInfo(data: RunDay) {
            with(binding){
                tvDay.text = data.day.toKorean()
                tvStartTime.text = data.startTime
                tvEndTime.text = data.endTime
                ivDelete.setOnClickListener {
                    clickListener.onClick(layoutPosition)
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<RunDay> {
        return RunDayInfoHolder(ItemRundayBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            .apply { setOnItemClickListener(clickListener) }
    }
}