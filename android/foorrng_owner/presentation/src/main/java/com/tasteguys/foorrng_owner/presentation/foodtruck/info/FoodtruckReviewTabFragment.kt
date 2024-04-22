package com.tasteguys.foorrng_owner.presentation.foodtruck.info

import android.os.Bundle
import android.view.View
import com.tasteguys.foorrng_owner.presentation.R
import com.tasteguys.foorrng_owner.presentation.base.BaseFragment
import com.tasteguys.foorrng_owner.presentation.databinding.FragmentFoodtruckReviewTabBinding
import com.tasteguys.foorrng_owner.presentation.model.foodtruck.FoodTruckInfo

class FoodtruckReviewTabFragment(
    private val foodTruckInfo: FoodTruckInfo
) : BaseFragment<FragmentFoodtruckReviewTabBinding>(
    FragmentFoodtruckReviewTabBinding::bind, R.layout.fragment_foodtruck_review_tab
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvReviewTotalCount.text = foodTruckInfo.reviewSet.totalCount.toString()
        binding.rvReviewList.adapter = ReviewListAdapter(foodTruckInfo.reviewSet)
    }
}