package com.tasteguys.foorrng_owner.presentation.location.regist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tasteguys.foorrng_owner.presentation.databinding.ItemRundayBinding
import com.tasteguys.foorrng_owner.presentation.model.mapper.toKorean
import com.tasteguys.foorrng_owner.presentation.model.run.RunDay

class RegistDayAdapter(
    val deleteBtnClickListener: (RunDay) -> Unit
) : ListAdapter<RunDay, RegistDayAdapter.RegistDayViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegistDayViewHolder {
        return RegistDayViewHolder(
            ItemRundayBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RegistDayViewHolder, position: Int) {
        holder.bind(getItem(position), deleteBtnClickListener)
    }

    class RegistDayViewHolder(
        private val binding: ItemRundayBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RunDay, deleteBtnClickListener: (RunDay) -> Unit) {
            binding.tvDay.text = item.day.toKorean()
            binding.tvStartTime.text = item.startTime
            binding.tvEndTime.text = item.endTime
            binding.ivDelete.setOnClickListener {
                deleteBtnClickListener(item)
            }
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<RunDay>() {
            override fun areItemsTheSame(oldItem: RunDay, newItem: RunDay): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: RunDay, newItem: RunDay): Boolean {
                return oldItem == newItem
            }
        }
    }
}