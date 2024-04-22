package com.tasteguys.foorrng_customer.presentation.truck.regist

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tasteguys.foorrng_customer.presentation.Dummy
import com.tasteguys.foorrng_customer.presentation.R
import com.tasteguys.foorrng_customer.presentation.base.BaseHolder
import com.tasteguys.foorrng_customer.presentation.base.GalleryLauncher
import com.tasteguys.foorrng_customer.presentation.base.WeekDaySelectManager
import com.tasteguys.foorrng_customer.presentation.base.toFile
import com.tasteguys.foorrng_customer.presentation.databinding.FragmentRegisterTruckBinding
import com.tasteguys.foorrng_customer.presentation.login.DailyFavoriteViewModel
import com.tasteguys.foorrng_customer.presentation.main.MainBaseFragment
import com.tasteguys.foorrng_customer.presentation.main.MainToolbarControl
import com.tasteguys.foorrng_customer.presentation.model.FavoriteCategory
import com.tasteguys.foorrng_customer.presentation.model.RunDay
import com.tasteguys.foorrng_customer.presentation.profile.adapter.DailyFavoriteListAdapter
import com.tasteguys.foorrng_customer.presentation.truck.TruckViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.net.URI
import java.time.DayOfWeek
import javax.inject.Inject
import kotlin.math.log

private const val TAG = "RegisterTruckFragment"

@AndroidEntryPoint
class RegisterTruckFragment @Inject constructor(
    private val isNew: Boolean,
    private val truckId: Long,
    private val truckViewModel: TruckViewModel
) : MainBaseFragment<FragmentRegisterTruckBinding>(
    { FragmentRegisterTruckBinding.bind(it) }, R.layout.fragment_register_truck
) {

    private val registerInputViewModel: RegisterInputViewModel by viewModels()
    private val dailyFavoriteViewModel: DailyFavoriteViewModel by activityViewModels()

    private val favoriteListAdapter = DailyFavoriteListAdapter()
    private val runDayAdapter = RunDayAdapter()

    private val galleryLauncher: GalleryLauncher by lazy {
        GalleryLauncher(this)
    }

    private lateinit var weekDaySelectManager: WeekDaySelectManager

    override fun setToolbar() {
        MainToolbarControl(
            visible = true,
            title = if(isNew) resources.getString(R.string.register_foodtruck) else resources.getString(R.string.update_foodtruck),
            menuRes = R.menu.menu_check,
        ).addMenuItemClickListener {
            confirmDialog()
        }.addNavIconClickListener {
            checkBackStackDialog()
        }.also {
            mainViewModel.changeToolbar(it)
        }
    }


    private val dayClickListener: (DayOfWeek, Boolean) -> Unit = { dayOfWeek, isOn ->
        showAddRunDayDialog(dayOfWeek)
    }

    private fun showAddRunDayDialog(dayOfWeek: DayOfWeek) {
        AddRundayDialog(requireContext(), dayOfWeek)
            .setCancelListener { dialog ->
                dialog.dismiss()
            }
            .setCreateListener { dialog, runDay ->
                registerInputViewModel.addRunDay(runDay)
                dialog.dismiss()
            }
            .show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        weekDaySelectManager = WeekDaySelectManager(binding.layoutSelectWeekday, dayClickListener)
        galleryLauncher.pictureCallbackListener = object : GalleryLauncher.PictureCallbackListener {
            override fun onGetData(data: Uri) {
                Glide.with(requireContext())
                    .load(data)
                    .fallback(R.drawable.logo_truck)
                    .centerCrop()
                    .circleCrop()
                    .into(binding.ivTruckPhoto)
                registerInputViewModel.inputPicture(data)
                registerInputViewModel.loadedPicture = data.toString()
                registerInputViewModel.imageChanged = true

            }
        }
        registerObserve()
    }

    private fun initView() {
        with(binding) {
            rvRuninfoRegist.apply{
                adapter = runDayAdapter.apply {
                    setOnItemClickListener(object : BaseHolder.ItemClickListener{
                        override fun onClick(position: Int) {
                            val day = currentList[position].day
                            registerInputViewModel.deleteRunDay(day)
                        }
                    })
                }
            }

            with(registerInputViewModel) {
                rvMenuCategory.apply {
                    layoutManager = FlexboxLayoutManager(context)
                    adapter = favoriteListAdapter.apply {
                        submitList(category.value)
                        setOnItemClickListener(object : BaseHolder.ItemClickListener {
                            override fun onClick(position: Int) {
                                val name = currentList[position].name
                                foodCategory[name] = !foodCategory[name]!!
                            }
                        })
                    }
                }
                foodCategory.clear()
                foodCategory.putAll( dailyFavoriteViewModel.getCategoryResult.value!!.getOrNull()!!.associateWith { false })

                tilTruckName.editText!!.addTextChangedListener { name = it.toString() }
                tilCallNumber.editText!!.addTextChangedListener { phoneNumber = it.toString() }
                tilCarNumber.editText!!.addTextChangedListener { carNumber = it.toString() }
                tilNotice.editText!!.addTextChangedListener { announcement = it.toString() }
                if (!isNew && !inputState) {
                    with(truckViewModel) {
                        val data = truckDetailResult.value!!.getOrNull()!!
                        val mainData = data.mainData
                        val opData = data.operation[0]
                        name = mainData.name
                        phoneNumber = mainData.phoneNumber
                        carNumber = mainData.carNumber
                        announcement = mainData.announcement
                        setMark(opData.address, opData.lat, opData.lng)
                        loadedPicture = mainData.picture
                        for(food in mainData.category){
                            if(food in foodCategory)
                                foodCategory[food] = true
                        }
                    }
                    inputState = true
                }
                tilTruckName.editText!!.setText(name)
                tilCallNumber.editText!!.setText(phoneNumber)
                tilCarNumber.editText!!.setText(carNumber)
                tilNotice.editText!!.setText(announcement)
                tilLocation.editText!!.setText(markAddress.value)
                Glide.with(requireContext())
                    .load(loadedPicture)
                    .error(R.drawable.bg_profile_photo)
                    .fallback(R.drawable.bg_profile_photo)
                    .centerCrop()
                    .circleCrop()
                    .into(binding.ivTruckPhoto)
                favoriteListAdapter.submitList(foodCategory.map { FavoriteCategory(it.key, it.value) })
            }

            tilLocation.visibility = if(isNew) View.VISIBLE else View.GONE
            layoutRundayRegist.visibility = if(isNew) View.VISIBLE else View.GONE

            tiLocation.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fcv_container, TruckSelectLocationFragment(isNew, registerInputViewModel))
                    .addToBackStack(null)
                    .commit()
            }

            ivTruckPhoto.setOnClickListener {
                galleryLauncher.openGallery()
            }
        }


    }

    private fun registerObserve() {
        with(registerInputViewModel){
            runDayList.observe(viewLifecycleOwner){lst->
                weekDaySelectManager.setSelectedDay(lst.keys)
                runDayAdapter.submitList(lst.values.toList())
            }
            markAddress.observe(viewLifecycleOwner) {
                binding.tilLocation.editText!!.setText(it)
            }
            registerResult.observe(viewLifecycleOwner){
                if (it.isSuccess) {
                    registerOperationInfo(it.getOrNull()!!.id)
                }
            }
            markRegisterResult.observe(viewLifecycleOwner){
                if(it.isSuccess){
                    // register하면 무조건 즐겨찾기에 들어간다
                    markFavoriteTruck(registerResult.value!!.getOrNull()!!.id)
                }
            }
            markFavoriteTruckResult.observe(viewLifecycleOwner) {
                if (it.isSuccess) {
                    showToast("푸드트럭 성공적으로 제보되었습니다.")
                    hideLoading()
                    parentFragmentManager.popBackStack()
                }
            }
            updateResult.observe(viewLifecycleOwner){
                if(it.isSuccess){
                    showToast("업데이트 성공")
                    hideLoading()
                    parentFragmentManager.popBackStack()

                }
            }

        }
    }

    private fun register() {
        showLoading()
        with(registerInputViewModel){
            registerTruck(
                name,
                picture.value.toFile(requireContext()),
                carNumber,
                announcement,
                phoneNumber,
                foodCategory.filter { it.value }.map { it.key }
            )
        }
    }

    private fun update() {
        showLoading()
        with(registerInputViewModel) {
            updateTruck(
                truckId,
                name,
                if(imageChanged) picture.value!!.toFile(requireContext()) else null,
                carNumber,
                announcement,
                phoneNumber,
                foodCategory.filter { it.value }.map { it.key }
            )
        }
    }


    private fun checkBackStackDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("등록을 취소하시겠습니까?")
            .setMessage("작성 중인 내용이 모두 삭제됩니다.")
            .setPositiveButton(resources.getString(R.string.btn_confirm)) { _, _ ->
                registerInputViewModel.initData()
                parentFragmentManager.popBackStack()
            }
            .setNegativeButton(resources.getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun confirmDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("푸드트럭 제보")
            .setMessage("푸드트럭을 제보하시겠습니까?")
            .setPositiveButton(resources.getString(R.string.btn_confirm)) { _, _ ->
                validateInput().onSuccess {
                    if (isNew) register() else update()
                }.onFailure {
                    showToast(it.message ?: "알 수 없는 오류가 발생했습니다.")
                }

            }
            .setNegativeButton(resources.getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun validateInput(): Result<String> {
        val nameValidation = binding.tilTruckName.editText!!.text.toString().isNotBlank()
        val carNumValidation = binding.tilCarNumber.editText!!.text.toString().let {
            it.isNotBlank() && it.matches(Regex("^[0-9]{2,3}[가-힣]\\s?[0-9]{4}$"))
        }
        val locationValidation = binding.tilLocation.editText!!.text.toString().isNotBlank()

        val msg = if (!nameValidation) {
            "이름을 입력해 주세요"
        } else if (!carNumValidation) {
            "차량 번호를 올바르게 입력해 주세요"
        } else if(!locationValidation){
            "위치 정보를 입력해 주세요"
        }
        else {
            return Result.success("success")
        }

        return Result.failure(Exception(msg))
    }


    override fun onDestroy() {
        with(registerInputViewModel) {
            if (!isNew && inputState) {
                inputState = false
            }
            imageChanged = false
            initData()
        }

        super.onDestroy()
    }

}