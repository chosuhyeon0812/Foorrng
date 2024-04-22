package com.tasteguys.foorrng_customer.presentation.profile

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.tasteguys.foorrng_customer.presentation.Dummy
import com.tasteguys.foorrng_customer.presentation.R
import com.tasteguys.foorrng_customer.presentation.base.BaseHolder
import com.tasteguys.foorrng_customer.presentation.base.LocationManager
import com.tasteguys.foorrng_customer.presentation.databinding.FragmentUserFavoriteBinding
import com.tasteguys.foorrng_customer.presentation.festival.FestivalListFragment
import com.tasteguys.foorrng_customer.presentation.login.DailyFavoriteViewModel
import com.tasteguys.foorrng_customer.presentation.main.MainBaseFragment
import com.tasteguys.foorrng_customer.presentation.main.MainToolbarControl
import com.tasteguys.foorrng_customer.presentation.model.FavoriteCategory
import com.tasteguys.foorrng_customer.presentation.profile.adapter.DailyFavoriteListAdapter
import com.tasteguys.foorrng_customer.presentation.profile.adapter.TruckAdapter
import com.tasteguys.foorrng_customer.presentation.truck.TruckViewModel
import com.tasteguys.foorrng_customer.presentation.truck.info.TruckInfoFragment
import com.tasteguys.foorrng_customer.presentation.truck.regist.RegisterTruckFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "UserFavoriteFragment"
@AndroidEntryPoint
class UserFavoriteFragment : MainBaseFragment<FragmentUserFavoriteBinding>(
    {FragmentUserFavoriteBinding.bind(it)}, R.layout.fragment_user_favorite
) {

    @Inject
    lateinit var locationManager: LocationManager

    private val favoriteAdapter = DailyFavoriteListAdapter()
    private val truckAdapter = TruckAdapter(false)
    private val truckViewModel: TruckViewModel by activityViewModels()

    private val userViewModel:UserViewModel by activityViewModels()
    private val dailyFavoriteViewModel: DailyFavoriteViewModel by activityViewModels()

    private val updateViewModel: FavoriteCategoryUpdateViewModel by viewModels()


    override fun setToolbar() {
        MainToolbarControl(
            true, resources.getString(R.string.my_favorite_info), backIcon = false, menuRes = R.menu.menu_option_menu
        ).also {
            mainViewModel.changeToolbar(it)
        }.addMenuItemClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fcv_container, FestivalListFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initView()
        registerObserve()
    }

    private fun initView(){
        truckViewModel.getFavoriteTruckList()
    }

    private fun initAdapter(){
        binding.rvFavoriteCategory.apply{
            adapter = favoriteAdapter.apply {
                setOnItemClickListener(object : BaseHolder.ItemClickListener{
                    override fun onClick(position: Int) {
                        val food = currentList[position].name
                        userViewModel.foodCategory[food] = !userViewModel.foodCategory[food]!!
                        with(updateViewModel){
                            if(!selectedList.contains(food)){
                                selectedList.add(food)
                            }else{
                                selectedList.remove(food)
                            }
                        }
                    }
                })
            }
            layoutManager = FlexboxLayoutManager(context).apply {
                flexWrap = FlexWrap.WRAP
                flexDirection = FlexDirection.ROW
                justifyContent = JustifyContent.CENTER
            }
        }

        binding.rvFavoriteTruck.apply{
            adapter = truckAdapter.apply {
                setOnItemClickListener(object : BaseHolder.ItemClickListener{
                    override fun onClick(position: Int) {
                        val curTruck = currentList[position]
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.fcv_container, TruckInfoFragment(curTruck.truckId, curTruck.name))
                            .addToBackStack(null)
                            .commit()
                    }
                })
                setOnButtonClickListener(object: TruckAdapter.TruckListHolder.ButtonClickListener{
                    override fun onToggleClick(position:Int) {
                        val curTruck = currentList[position]
                        truckViewModel.markFavoriteTruck(curTruck.truckId)
                    }

                    override fun onButtonClick(position:Int) {
                    }

                })
                layoutManager = LinearLayoutManager(context).apply {
                    orientation = LinearLayoutManager.VERTICAL
                }
            }
        }

        binding.btnUpdateFavoriteCategory.setOnClickListener {
            val latLng = locationManager.getCurrentLocation()
            showLoading()
            updateViewModel.updateCategory(latLng.latitude, latLng.longitude)
        }

    }

    private fun registerObserve(){
        truckViewModel.favoriteTruckListResult.observe(viewLifecycleOwner){ res->
            if(res.isSuccess){
                res.getOrNull()?.let {
                    truckAdapter.submitList(it)
                }
            }
        }

        truckViewModel.markFavoriteTruckResult.observe(viewLifecycleOwner){
            if(it.isSuccess){
                truckViewModel.getFavoriteTruckList()
            }
        }

        dailyFavoriteViewModel.getCategoryResult.observe(viewLifecycleOwner){res->
            if(res.isSuccess){
                userViewModel.foodCategory.putAll(res.getOrNull()!!.associateWith { false })
                userViewModel.getUserData()
            }
        }
        userViewModel.getUserResult.observe(viewLifecycleOwner){res->
            if(res.isSuccess){
                val category = res.getOrNull()!!.favoriteCategory
                for(food in category){
                    userViewModel.foodCategory[food] = true
                    updateViewModel.selectedList.add(food)
                }
                favoriteAdapter.submitList(userViewModel.foodCategory.map { FavoriteCategory(it.key, it.value) })
            }
        }
        updateViewModel.updateResult.observe(viewLifecycleOwner){
            if(it.isSuccess){
                hideLoading()
                showToast("선호 정보 업데이트 완료")
            }else{
                showToast("오류")
            }
        }
    }

}