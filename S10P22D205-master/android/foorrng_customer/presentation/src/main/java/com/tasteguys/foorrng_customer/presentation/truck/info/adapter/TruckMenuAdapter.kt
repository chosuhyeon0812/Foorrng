package com.tasteguys.foorrng_customer.presentation.truck.info.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.tasteguys.foorrng_customer.presentation.R
import com.tasteguys.foorrng_customer.presentation.base.BaseAdapter
import com.tasteguys.foorrng_customer.presentation.base.BaseHolder
import com.tasteguys.foorrng_customer.presentation.databinding.ItemMenuBinding
import com.tasteguys.foorrng_customer.presentation.databinding.ItemMenuSimpleBinding
import com.tasteguys.foorrng_customer.presentation.model.TruckMenu

class TruckMenuAdapter( private var isSimple : Boolean = true, private var isEditable: Boolean = false) : BaseAdapter<TruckMenu>() {

    class SimpleMenuHolder(private val binding: ItemMenuSimpleBinding) : BaseHolder<TruckMenu>(binding) {
        @SuppressLint("SetTextI18n")
        override fun bindInfo(data: TruckMenu) {
            binding.tvMenuName.text = data.name
            binding.tvMenuPrice.text = "${data.price}원"
        }
    }

    class MenuHolder(private val binding: ItemMenuBinding, private val isEditable: Boolean) : BaseHolder<TruckMenu>(binding) {
        @SuppressLint("SetTextI18n")
        override fun bindInfo(data: TruckMenu) {
            with(binding){
                Glide.with(root)
                    .load(data.img)
                    .error(R.drawable.logo_truck)
                    .into(ivMenuImg)
                tvMenuName.text = data.name
                tvMenuPrice.text = "${data.price}원"
                btnEdit.setOnClickListener {
                    clickListener.onClick(layoutPosition)
                }
                if(!isEditable){
                    btnEdit.visibility = View.GONE
                }
                root.setOnClickListener{
                    clickListener.onClick(layoutPosition)
                }
            }

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<TruckMenu> {
        return if(isSimple){
            SimpleMenuHolder(
                ItemMenuSimpleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }else{
            MenuHolder(
                ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false), isEditable
            ).apply {
                setOnItemClickListener(clickListener)
            }
        }
    }
}