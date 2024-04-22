package com.tasteguys.foorrng_owner.presentation.foodtruck.menu

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tasteguys.foorrng_owner.presentation.R
import com.tasteguys.foorrng_owner.presentation.databinding.FragmentMenuEditBinding
import com.tasteguys.foorrng_owner.presentation.main.MainBaseFragment
import com.tasteguys.foorrng_owner.presentation.main.MainToolbarControl
import com.tasteguys.foorrng_owner.presentation.model.foodtruck.Menu
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.text.SimpleDateFormat

@AndroidEntryPoint
class MenuEditFragment(
    private val menu: Menu? = null
) : MainBaseFragment<FragmentMenuEditBinding>(
    FragmentMenuEditBinding::bind, R.layout.fragment_menu_edit
) {
    private val menuEditViewModel: MenuEditViewModel by viewModels()

    private val title = if (menu == null) "메뉴 추가" else "메뉴 수정"
    private var photoFile: File? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity.onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            checkBackStackDialog()
        }

        setUi_Listener()
        registerObserve()
    }

    private fun registerObserve(){
        menuEditViewModel.menuRegistResult.observe(viewLifecycleOwner){
            it.onSuccess {
                hideLoading()
                parentFragmentManager.popBackStack()
            }.onFailure {
                hideLoading()
                showToast(it.message.toString())
            }
        }

        menuEditViewModel.menuModifyResult.observe(viewLifecycleOwner){
            it.onSuccess {
                hideLoading()
                parentFragmentManager.popBackStack()
            }.onFailure {
                hideLoading()
                showToast(it.message.toString())
            }
        }
    }

    private fun setUi_Listener() {
        binding.btnChangeImage.setOnClickListener {
            showPicOrGalDialog()
        }

        if (menu == null) {
            setCreateUi()
        } else {
            setEditUi()
        }

    }

    private fun setCreateUi() {
        binding.btnSave.setOnClickListener {
            val name = binding.etMenuName.text.toString()
            val price = binding.etMenuPrice.text.toString()
            val image = photoFile

            if (name.isEmpty() || price.isBlank() || image == null) {
                showToast("모든 항목을 입력해주세요.")
            } else {
                showLoading()
                menuEditViewModel.registMenu(name, price.toInt(), image)
            }
        }
    }

    private fun setEditUi() {
        menu?.let {
            Glide.with(binding.root)
                .load(it.imageUrl)
                .centerCrop()
                .into(binding.ivMenuImage)

            binding.etMenuName.setText(it.name)
            binding.etMenuPrice.setText(it.price.toString())
        }

        binding.btnSave.setOnClickListener {
            showLoading()
            val name = binding.etMenuName.text.toString()
            val price = binding.etMenuPrice.text.toString()
            val image = photoFile

            if (name.isBlank() || price.isBlank()) {
                showToast("모든 항목을 입력해주세요.")
            } else {
                menuEditViewModel.modifyMenu(menu!!.id, name, price.toInt(), image)
            }
        }
    }

    override fun setToolbar() {
        mainViewModel.changeToolbar(
            MainToolbarControl(
                visible = true,
                title = title
            ).addNavIconClickListener {
                checkBackStackDialog()
            }
        )
    }

    private fun checkBackStackDialog() {
        MaterialAlertDialogBuilder(_activity)
            .setTitle("등록을 취소하시겠습니까?")
            .setMessage("작성 중인 내용이 모두 삭제됩니다.")
            .setPositiveButton("확인") { _, _ ->
                parentFragmentManager.popBackStack()
            }
            .setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }


    // region 이미지 관련 함수
    private fun showPicOrGalDialog() {
        MaterialAlertDialogBuilder(_activity)
            .setTitle("어느 곳에서 사진을 가져올까요?")
            .setNeutralButton("취소") { _, _ -> }
            .setNegativeButton("갤러리") { _, _ ->
                getImageFromGallery()
            }
            .setPositiveButton("사진찍기") { _, _ ->
                getImageFromPicture()
            }
            .show()
    }


    private fun getRealPathFromURI(uri: Uri): String {
        val buildName = Build.MANUFACTURER
        if (buildName == "Xiomi") return uri.path!!

        var columnIndex = 0
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = _activity.contentResolver.query(uri, proj, null, null, null)
        if (cursor!!.moveToFirst()) {
            columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        }
        val result = cursor.getString(columnIndex)
        cursor.close()
        return result
    }

    private fun getImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.setDataAndType(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            "image/*"
        )
        galleryResult.launch(intent)
    }

    private fun getImageFromPicture() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        photoFile = createImageFile()
        var photoUri =
            FileProvider.getUriForFile(
                _activity,
                "com.tasteguys.foorrng_owner.fileprovider",
                photoFile!!
            )
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
        requestPicture.launch(intent)
    }

    val requestPicture =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                Glide.with(this)
                    .load(Uri.fromFile(photoFile))
                    .centerCrop()
                    .into(binding.ivMenuImage)
            }
        }

    private var currnetPhotoPath: String? = null
    private fun createImageFile(): File {
        val timeString = SimpleDateFormat("yyyy-MM-dd HH:mm ").format(System.currentTimeMillis())
        val imageNamePrefix = "relay_stop_pic"
        val storageDir = _activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!

        return File.createTempFile(
            timeString + imageNamePrefix,
            ".jpg",
            storageDir
        ).apply { currnetPhotoPath = absolutePath }
    }

    private val galleryResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imgUri = result.data?.data
                imgUri.let {
                    photoFile = File(getRealPathFromURI(it!!))
                    Glide.with(this)
                        .load(imgUri)
                        .centerCrop()
                        .into(binding.ivMenuImage)
                }
            }
        }
    // endregion 이미지 관련 함수

}