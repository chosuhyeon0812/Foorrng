package com.tasteguys.foorrng_customer.presentation.truck.info.adapter

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tasteguys.foorrng_customer.domain.model.truck.TruckDetailMarkData
import com.tasteguys.foorrng_customer.presentation.R
import com.tasteguys.foorrng_customer.presentation.base.BaseAdapter
import com.tasteguys.foorrng_customer.presentation.base.BaseHolder
import com.tasteguys.foorrng_customer.presentation.base.collapse
import com.tasteguys.foorrng_customer.presentation.base.expand
import com.tasteguys.foorrng_customer.presentation.databinding.ItemMarkInfoBinding
import com.tasteguys.foorrng_customer.presentation.model.TruckMark

private const val TAG = "TruckMarkInfoAdapter"
class TruckMarkInfoAdapter: BaseAdapter<TruckDetailMarkData>() {

    class MarkInfoHolder(private val binding: ItemMarkInfoBinding) : BaseHolder<TruckDetailMarkData>(binding){
        private val checkedDay = mapOf(
            "월" to 0, "화" to 1, "수" to 2, "목" to 3, "금" to 4, "토" to 5, "일" to 6
        )
        override fun bindInfo(data: TruckDetailMarkData) {
            with(binding) {
                tvRoadInfo.text = data.address
                val span = SpannableStringBuilder(tvDayInfo.text)
                for (day in data.operationInfoList) {
                    val dayIdx = checkedDay[day.day]!!
                    span.setSpan(
                        ForegroundColorSpan(root.resources.getColor(R.color.foorrng_orange_dark)),
                        dayIdx,
                        dayIdx + 1,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
                tvDayInfo.text = span
                rvDayInfo.apply {
                    adapter = TruckOperationInfoAdapter().apply {
                        submitList(data.operationInfoList)
                    }
                }

                btnExpandToggle.setOnClickListener {
                    if (rvDayInfo.visibility == View.GONE){
                        rvDayInfo.expand()
                    } else if (rvDayInfo.visibility == View.VISIBLE) {
                        rvDayInfo.collapse()
                    }
                }

            }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<TruckDetailMarkData> {
        return MarkInfoHolder(ItemMarkInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            .apply { setOnItemClickListener(clickListener) }
    }
}