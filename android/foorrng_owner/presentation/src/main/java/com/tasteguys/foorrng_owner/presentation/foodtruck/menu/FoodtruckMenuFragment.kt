package com.tasteguys.foorrng_owner.presentation.foodtruck.menu

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.tasteguys.foorrng_owner.presentation.R
import com.tasteguys.foorrng_owner.presentation.databinding.FragmentFoodMenuBinding
import com.tasteguys.foorrng_owner.presentation.main.MainBaseFragment
import com.tasteguys.foorrng_owner.presentation.main.MainToolbarControl
import com.tasteguys.foorrng_owner.presentation.model.foodtruck.Menu
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodtruckMenuFragment : MainBaseFragment<FragmentFoodMenuBinding>(
    FragmentFoodMenuBinding::bind, R.layout.fragment_food_menu
) {
    private val foodtruckMenuViewModel: FoodtruckMenuViewModel by viewModels()

    private var menuAdapter: MenuListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        foodtruckMenuViewModel.getMenuList().also {
            showLoading()
        }

        registerListener()
        registerObserve()
    }

    private fun registerListener() {
        binding.btnAddMenu.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.layout_main_fragment, MenuEditFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun registerObserve() {
        foodtruckMenuViewModel.menuList.observe(viewLifecycleOwner) {
            hideLoading()
            it.onSuccess {
                setMenuList(it)
            }.onFailure {
                showToast(it.message.toString())
//                parentFragmentManager.popBackStack()
            }
        }
    }

    private fun setMenuList(list: List<Menu>) {
        menuAdapter = MenuListAdapter(list) {
            parentFragmentManager.beginTransaction()
                .replace(R.id.layout_main_fragment, MenuEditFragment(it))
                .addToBackStack(null)
                .commit()
        }
        binding.rvFoodtruckMenu.adapter = menuAdapter
    }

    override fun setToolbar() {
        mainViewModel.changeToolbar(
            MainToolbarControl(
                title = "메뉴 관리",
                visible = true,
            ).addNavIconClickListener {
                parentFragmentManager.popBackStack()
            }
        )
    }
}