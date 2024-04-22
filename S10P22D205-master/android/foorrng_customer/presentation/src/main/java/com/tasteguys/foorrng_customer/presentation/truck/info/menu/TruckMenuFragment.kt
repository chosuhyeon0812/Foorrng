package com.tasteguys.foorrng_customer.presentation.truck.info.menu

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.tasteguys.foorrng_customer.presentation.R
import com.tasteguys.foorrng_customer.presentation.base.BaseHolder
import com.tasteguys.foorrng_customer.presentation.databinding.FragmentTruckMenuBinding
import com.tasteguys.foorrng_customer.presentation.main.MainBaseFragment
import com.tasteguys.foorrng_customer.presentation.main.MainToolbarControl
import com.tasteguys.foorrng_customer.presentation.model.mapper.toTruckMenu
import com.tasteguys.foorrng_customer.presentation.truck.TruckViewModel
import com.tasteguys.foorrng_customer.presentation.truck.info.adapter.TruckMenuAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TruckMenuFragment @Inject constructor(
    private val truckId: Long,
    private val truckViewModel: TruckViewModel
) : MainBaseFragment<FragmentTruckMenuBinding>(
    { FragmentTruckMenuBinding.bind(it)}, R.layout.fragment_truck_menu
) {

    private val truckMenuViewModel: TruckMenuViewModel by viewModels()
    private val truckMenuAdapter = TruckMenuAdapter(false)

    override fun setToolbar() {
        MainToolbarControl(
            true, resources.getString(R.string.menu)
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
        truckMenuViewModel.getMenuList(truckId)

        binding.rvMenu.apply {
            adapter = truckMenuAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))
        }

        binding.btnAddMenu.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fcv_container, TruckMenuRegisterFragment(truckId, null))
                .addToBackStack(null)
                .commit()
        }
    }

    private fun registerObserve(){
        truckViewModel.truckDetailResult.observe(viewLifecycleOwner){ res->
            if(res.isSuccess){
                truckMenuAdapter.apply{
                    submitList(
                        res.getOrNull()?.menus!!.map { it.toTruckMenu() }
                    )
                    setOnItemClickListener(object : BaseHolder.ItemClickListener{
                        override fun onClick(position: Int) {
                            parentFragmentManager.beginTransaction()
                                .replace(R.id.fcv_container, TruckMenuRegisterFragment(truckId, currentList[position]))
                                .addToBackStack(null)
                                .commit()
                        }
                    })
                }
                if(res.getOrNull()!!.type=="foodtruck"){
                    binding.btnAddMenu.visibility = View.GONE
                }
            }
        }
        truckMenuViewModel.getMenuListResult.observe(viewLifecycleOwner){ res->
            if(res.isSuccess){
                truckMenuAdapter.apply{
                    submitList(
                        res.getOrNull()?.map { it.toTruckMenu() }
                    )
                    setOnItemClickListener(object : BaseHolder.ItemClickListener{
                        override fun onClick(position: Int) {
                            parentFragmentManager.beginTransaction()
                                .replace(R.id.fcv_container, TruckMenuRegisterFragment(truckId, currentList[position]))
                                .addToBackStack(null)
                                .commit()
                        }
                    })
                }

            }
        }
    }

}