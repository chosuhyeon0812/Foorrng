package com.tasteguys.foorrng_customer.presentation.base

import android.annotation.SuppressLint
import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

private const val TAG = "BaseAdapter"
abstract class BaseHolder<T>(binding: ViewBinding): RecyclerView.ViewHolder(binding.root){
    abstract fun bindInfo(data: T)

    interface ItemClickListener{ fun onClick(position: Int) }

    protected lateinit var clickListener: ItemClickListener
    fun setOnItemClickListener(listener: ItemClickListener){ clickListener = listener }
}



abstract class BaseAdapter<T : Any>(protected open var clickListener: BaseHolder.ItemClickListener
    = object: BaseHolder.ItemClickListener{
    override fun onClick(position: Int) {}}
) : ListAdapter<T, BaseHolder<T>>(
    object: DiffUtil.ItemCallback<T>(){
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
//            return System.identityHashCode(oldItem) == System.identityHashCode(newItem)
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem == newItem
        }
    }
){

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<T>
    override fun onBindViewHolder(holder: BaseHolder<T>, position: Int){
        holder.bindInfo(getItem(position))
    }

    fun setOnItemClickListener(listener: BaseHolder.ItemClickListener){ clickListener = listener }


}