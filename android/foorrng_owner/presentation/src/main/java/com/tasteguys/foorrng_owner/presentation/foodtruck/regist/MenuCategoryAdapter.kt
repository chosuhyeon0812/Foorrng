package com.tasteguys.foorrng_owner.presentation.foodtruck.regist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.tasteguys.foorrng_owner.presentation.databinding.ItemMenuCategoryBinding

class MenuCategoryAdapter(
    private val menuCategoryList: List<String>
) : RecyclerView.Adapter<MenuCategoryAdapter.MenuCategoryViewHolder>() {
    private val selectedCategoryList = MutableList(menuCategoryList.size){false}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuCategoryViewHolder {
        return MenuCategoryViewHolder(
            ItemMenuCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            selectedCategoryList
        )
    }

    override fun onBindViewHolder(holder: MenuCategoryViewHolder, position: Int) {
        holder.binding(menuCategoryList[position],position)
    }

    override fun getItemCount(): Int {
        return menuCategoryList.size
    }

    fun setSelectCategoryList(selectedCategoryList: List<String>){
        selectedCategoryList.forEach { category ->
            val index = menuCategoryList.indexOf(category)
            if (index != -1){
                this.selectedCategoryList[index] = true
                this.notifyItemChanged(index)
            }
        }
    }

    fun getSelectedCategoryList(): List<String>{
        return menuCategoryList.filterIndexed { index, _ ->
            selectedCategoryList[index]
        }
    }

    class MenuCategoryViewHolder(
        private val binding: ItemMenuCategoryBinding,
        private val selectedCategoryList: MutableList<Boolean>
    ) : RecyclerView.ViewHolder(binding.root) {
        fun binding(item: String,position: Int){
            binding.chipCategory.text = item
            binding.chipCategory.isChecked = selectedCategoryList[position]
            binding.chipCategory.setOnClickListener {
                selectedCategoryList[position] = (it as Chip).isChecked
            }
        }
    }
}