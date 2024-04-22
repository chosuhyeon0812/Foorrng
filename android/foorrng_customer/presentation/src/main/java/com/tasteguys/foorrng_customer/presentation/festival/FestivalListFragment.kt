package com.tasteguys.foorrng_customer.presentation.festival

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.tasteguys.foorrng_customer.presentation.R
import com.tasteguys.foorrng_customer.presentation.base.BaseHolder
import com.tasteguys.foorrng_customer.presentation.databinding.FragmentFestivalListBinding
import com.tasteguys.foorrng_customer.presentation.festival.regist.RegisterFestivalFragment
import com.tasteguys.foorrng_customer.presentation.main.MainBaseFragment
import com.tasteguys.foorrng_customer.presentation.main.MainToolbarControl
import com.tasteguys.foorrng_customer.presentation.truck.regist.RegisterTruckFragment

private const val TAG = "FestivalListFragment"
class FestivalListFragment : MainBaseFragment<FragmentFestivalListBinding>(
    {FragmentFestivalListBinding.bind(it)}, R.layout.fragment_festival_list
) {

    private val festivalViewModel: FestivalViewModel by activityViewModels()
    private val festivalAdapter =  FestivalAdapter()

    override fun setToolbar() {
        MainToolbarControl(
            true, resources.getString(R.string.my_article)
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
        festivalViewModel.getFestivalList()
        binding.rvFestivalList.apply {
            adapter = festivalAdapter.apply {
                setOnItemClickListener(object:BaseHolder.ItemClickListener{
                    override fun onClick(position: Int) {
                        Log.d(TAG, "onClick: ")
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.fcv_container ,FestivalDetailFragment(currentList[position].id))
                            .addToBackStack(null)
                            .commit()
                    }
                })
            }
        }

        binding.btnRegister.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fcv_container, RegisterFestivalFragment(true, -1))
                .addToBackStack(null)
                .commit()
        }
    }

    private fun registerObserve(){
        with(festivalViewModel){
            getListResult.observe(viewLifecycleOwner){
                if(it.isSuccess){
                    festivalAdapter.submitList(it.getOrNull()!!)
                }
            }
        }
    }
}