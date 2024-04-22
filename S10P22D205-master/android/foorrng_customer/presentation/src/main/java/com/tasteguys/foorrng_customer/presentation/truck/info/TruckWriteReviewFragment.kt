package com.tasteguys.foorrng_customer.presentation.truck.info

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.tasteguys.foorrng_customer.presentation.R
import com.tasteguys.foorrng_customer.presentation.base.BaseHolder
import com.tasteguys.foorrng_customer.presentation.databinding.FragmentTruckWriteReviewBinding
import com.tasteguys.foorrng_customer.presentation.main.MainBaseFragment
import com.tasteguys.foorrng_customer.presentation.main.MainToolbarControl
import com.tasteguys.foorrng_customer.presentation.model.mapper.ReviewMap
import com.tasteguys.foorrng_customer.presentation.truck.TruckViewModel
import com.tasteguys.foorrng_customer.presentation.truck.info.adapter.TruckReviewBtnAdapter
import com.tasteguys.retrofit_adapter.FoorrngException
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "TruckWriteReviewFragmen"

@AndroidEntryPoint
class TruckWriteReviewFragment @Inject constructor(
    private val truckId: Long,
    private val truckName: String,
    private val truckViewModel: TruckViewModel
) : MainBaseFragment<FragmentTruckWriteReviewBinding>(
    { FragmentTruckWriteReviewBinding.bind(it)}, R.layout.fragment_truck_write_review
)  {

    private val reviewViewModel: TruckReviewViewModel by viewModels()

    override fun setToolbar() {
        MainToolbarControl(
            true, truckName
        ).also {
            mainViewModel.changeToolbar(it)
        }.addNavIconClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        registerObserve()
    }

    private fun initView(){
        with(binding){
            rvReviewBtn.apply {
                adapter = TruckReviewBtnAdapter().apply {
                    submitList(ReviewMap.reviewMap.keys.toList())
                    setOnItemClickListener(object : BaseHolder.ItemClickListener{
                        override fun onClick(position: Int) {
                            val rv = ReviewMap.registerReviewMap[currentList[position]]!!
                            with(reviewViewModel){
                                if(!selectedReview.contains(rv)){
                                    selectedReview.add(rv)
                                }else{
                                    selectedReview.remove(rv)
                                }
                            }
                        }
                    })
                }
            }
        }

        Glide.with(requireContext())
            .load(truckViewModel.truckDetailResult.value!!.getOrNull()!!.mainData.picture)
            .fallback(R.drawable.bg_profile_photo)
            .error(R.drawable.bg_profile_photo)
            .centerCrop()
            .circleCrop()
            .into(binding.civTruckImg)

        binding.btnRegister.setOnClickListener {
            reviewViewModel.registerReview(truckId)
        }
    }

    private fun registerObserve(){
        reviewViewModel.registerReviewResult.observe(viewLifecycleOwner){
            if(it.isSuccess){
                showToast("성공적으로 등록됐습니다")
                parentFragmentManager.popBackStack()
            }else{
                it.exceptionOrNull()?.let{t->
                    if(t is FoorrngException && t.code == FoorrngException.ALREADY_REVIEW){
                        showSnackBar(t.message)
                    }else{
                        showSnackBar("오류")
                    }
                }
            }
        }
    }

}