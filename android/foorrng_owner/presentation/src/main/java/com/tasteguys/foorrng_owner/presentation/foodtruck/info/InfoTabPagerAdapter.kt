package com.tasteguys.foorrng_owner.presentation.foodtruck.info

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tasteguys.foorrng_owner.presentation.model.foodtruck.FoodTruckInfo

class InfoTabPagerAdapter(
    fragment: Fragment,
    private var foodTruckInfo: FoodTruckInfo
): FragmentStateAdapter(fragment) {

    private val fragments: List<Fragment> = listOf(
        FoodtruckInfoTabFragment(foodTruckInfo),
        FoodtruckReviewTabFragment(foodTruckInfo)
    )

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    override fun getItemCount(): Int {
        return fragments.size
    }
}