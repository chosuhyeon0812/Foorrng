package com.tasteguys.foorrng_owner.presentation.location.manage

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tasteguys.foorrng_owner.presentation.databinding.ItemLocationDayBinding
import com.tasteguys.foorrng_owner.presentation.model.location.RunInfo
import java.time.DayOfWeek

private const val TAG = "RunInfoAdapter_poorrng"
class RunInfoAdapter(
    private val runInfoList: List<RunInfo>
) : RecyclerView.Adapter<RunInfoAdapter.RunInfoViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunInfoViewHolder {
        Log.d(TAG, "onCreateViewHolder: ")
        return RunInfoViewHolder(
            ItemLocationDayBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RunInfoViewHolder, position: Int) {
        holder.bind(runInfoList[position])
    }

    override fun getItemCount(): Int {
        return runInfoList.size
    }

    class RunInfoViewHolder(
        private val binding: ItemLocationDayBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RunInfo) {
            Log.d(TAG, "bind: $item")
            binding.tvWeekday.text = "${getDayOfWeekString(item.day)}요일"
            binding.tvTime.text = "${item.startTime} ~ ${item.endTime}"
        }

        private fun getDayOfWeekString(day: DayOfWeek): String {
            return when(day.value) {
                1 -> "월"
                2 -> "화"
                3 -> "수"
                4 -> "목"
                5 -> "금"
                6 -> "토"
                7 -> "일"
                else -> ""
            }
        }
    }
}