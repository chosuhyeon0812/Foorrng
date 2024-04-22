package com.tasteguys.foorrng_customer.presentation.truck.info.menu

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tasteguys.foorrng_customer.presentation.R
import com.tasteguys.foorrng_customer.presentation.base.GalleryLauncher
import com.tasteguys.foorrng_customer.presentation.base.toFile
import com.tasteguys.foorrng_customer.presentation.databinding.FragmentTruckMenuRegisterBinding
import com.tasteguys.foorrng_customer.presentation.main.MainBaseFragment
import com.tasteguys.foorrng_customer.presentation.main.MainToolbarControl
import com.tasteguys.foorrng_customer.presentation.model.TruckMenu
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TruckMenuRegisterFragment(private val truckId: Long, private val data: TruckMenu?) : MainBaseFragment<FragmentTruckMenuRegisterBinding>(
    {FragmentTruckMenuRegisterBinding.bind(it)}, R.layout.fragment_truck_menu_register
) {

    private val truckMenuViewModel: TruckMenuViewModel by viewModels()
    private val galleryLauncher: GalleryLauncher by lazy {
        GalleryLauncher(this)
    }

    override fun setToolbar() {
        MainToolbarControl(
            visible = true,
            title = if(data==null) resources.getString(R.string.register_menu) else resources.getString(R.string.update_menu),
            menuRes = if(data==null) 0 else R.menu.menu_delete
        ).addMenuItemClickListener {
            checkDeleteDialog()
        }.addNavIconClickListener {
            checkBackStackDialog()
        }.also {
            mainViewModel.changeToolbar(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        registerObserve()
    }

    private fun initView(){
        with(binding){
            if(data != null){
                Glide.with(requireContext())
                    .load(data.img)
                    .fallback(R.drawable.bg_profile_photo)
                    .into(ivMenuImage)
                tilMenuName.editText!!.setText(data.name)
                truckMenuViewModel.name = data.name
                tilMenuPrice.editText!!.setText(data.price.toString())
                truckMenuViewModel.price = data.price.toLong()
            }
            with(truckMenuViewModel){
                tilMenuName.editText!!.addTextChangedListener {name = it.toString()}
                tilMenuPrice.editText!!.addTextChangedListener { price = it.toString().toLong() }

                galleryLauncher.pictureCallbackListener = object : GalleryLauncher.PictureCallbackListener {
                    override fun onGetData(data: Uri) {
                        Glide.with(requireContext())
                            .load(data)
                            .fallback(R.drawable.logo_truck)
                            .into(ivMenuImage)
                        img = data.toFile(requireContext())
                    }
                }
            }
            btnSave.setOnClickListener {
                if(data==null){
                    truckMenuViewModel.registerMenu(truckId)
                }else{
                    truckMenuViewModel.updateMenu(data.id, truckId)
                }
            }
            btnChangeImage.setOnClickListener {
                galleryLauncher.openGallery()
            }

        }
    }

    private fun registerObserve(){
        with(truckMenuViewModel){
            registerResult.observe(viewLifecycleOwner){
                if(it.isSuccess){
                    showToast("등록 성공")
                    parentFragmentManager.popBackStack()
                }else{
                    showToast("오류")
                }
            }
            updateResult.observe(viewLifecycleOwner){
                if(it.isSuccess){
                    showToast("수정 성공")
                    parentFragmentManager.popBackStack()
                }else{
                    showToast("오류")
                }
            }
            deleteResult.observe(viewLifecycleOwner){
                if(it.isSuccess){
                    showToast("삭제 성공")
                    parentFragmentManager.popBackStack()
                }else{
                    showToast("오류")
                }
            }
        }

    }

    private fun checkBackStackDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("등록을 취소하시겠습니까?")
            .setMessage("작성 중인 내용이 모두 삭제됩니다.")
            .setPositiveButton(resources.getString(R.string.btn_confirm)) { _, _ ->
                parentFragmentManager.popBackStack()
            }
            .setNegativeButton(resources.getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun checkDeleteDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("삭제 하시겠습니까?")
            .setMessage("삭제된 내용은 복구 할 수 없습니다.")
            .setPositiveButton(resources.getString(R.string.btn_confirm)) { _, _ ->
                truckMenuViewModel.deleteMenu(data!!.id)
            }
            .setNegativeButton(resources.getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

}