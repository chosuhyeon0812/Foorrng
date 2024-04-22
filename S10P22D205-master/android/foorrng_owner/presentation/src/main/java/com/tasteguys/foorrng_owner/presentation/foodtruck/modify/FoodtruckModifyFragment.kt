package com.tasteguys.foorrng_owner.presentation.foodtruck.modify

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
import com.bumptech.glide.request.RequestOptions
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tasteguys.foorrng_owner.presentation.R
import com.tasteguys.foorrng_owner.presentation.databinding.FragmentFoodtruckModifyBinding
import com.tasteguys.foorrng_owner.presentation.foodtruck.regist.MenuCategoryAdapter
import com.tasteguys.foorrng_owner.presentation.main.MainBaseFragment
import com.tasteguys.foorrng_owner.presentation.main.MainToolbarControl
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.text.SimpleDateFormat

@AndroidEntryPoint
class FoodtruckModifyFragment : MainBaseFragment<FragmentFoodtruckModifyBinding>(
    FragmentFoodtruckModifyBinding::bind, R.layout.fragment_foodtruck_modify
) {
    private val foodtruckModifyViewModel: FoodtruckModifyViewModel by viewModels()

    private var menuCategoryAdapter: MenuCategoryAdapter? = null

    private var photoFile: File? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCategoryView()
        setCategoryViewAdapter(dummyCategory.sortedBy { it.length })

        mainActivity.onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            checkBackStackDialog()
        }

        init()
        registerObserve()
        registerListener()
    }

    private fun init() {
        mainViewModel.foodtruckInfo.value?.peekContent()?.getOrNull()?.let {
            binding.tilFoodtruckName.editText!!.setText(it.name)
            binding.tilCarNumber.editText!!.setText(it.carNumber)
            binding.tilAccountNumber.editText!!.setText(it.accountInfo)
            binding.tilCallNumber.editText!!.setText(it.callNumber)
            binding.tilNotice.editText!!.setText(it.notice)
            Glide.with(this)
                .load(it.pictureUrl)
                .centerCrop()
                .apply(RequestOptions().circleCrop())
                .into(binding.ivTruckPhoto)
            menuCategoryAdapter?.setSelectCategoryList(it.category)
        } ?: run {
            showToast("푸드트럭 정보를 불러오는데 실패했습니다.")
            parentFragmentManager.popBackStack()
        }
    }

    private fun registerListener() {
        binding.ivTruckPhoto.setOnClickListener {
            showPicOrGalDialog()
        }
    }

    private fun registerObserve() {
        foodtruckModifyViewModel.foodtruckModifyResult.observe(viewLifecycleOwner) {
            hideLoading()
            it.onSuccess {
                showToast("수정이 완료되었습니다.")
                mainViewModel.getFoodtruckInfo()
                parentFragmentManager.popBackStack()
            }.onFailure {
                showToast(it.message ?: "알 수 없는 오류가 발생했습니다.")
            }
        }
    }

    private fun initCategoryView() {
        binding.rvMenuCategory.layoutManager = FlexboxLayoutManager(_activity)
    }

    private fun setCategoryViewAdapter(menuList: List<String>) {
        if (menuCategoryAdapter == null) {
            menuCategoryAdapter = MenuCategoryAdapter(menuList)
        }
        binding.rvMenuCategory.adapter = menuCategoryAdapter
    }

    override fun setToolbar() {
        MainToolbarControl(
            visible = true,
            title = "푸드트럭 수정",
            menuRes = R.menu.menu_check
        ).addMenuItemClickListener {
            modify().also {
                showLoading()
            }
        }.addNavIconClickListener {
            checkBackStackDialog()
        }.also {
            mainViewModel.changeToolbar(it)
        }
    }

    private fun modify() {
        validateInput()
            .onSuccess {
                if (menuCategoryAdapter != null) {
                    foodtruckModifyViewModel.updateFoodtruck(
                        binding.tilFoodtruckName.editText!!.text.toString(),
                        binding.tilCarNumber.editText!!.text.toString(),
                        binding.tilAccountNumber.editText!!.text.toString(),
                        binding.tilCallNumber.editText!!.text.toString(),
                        binding.tilNotice.editText!!.text.toString(),
                        menuCategoryAdapter!!.getSelectedCategoryList(),
                        photoFile
                    )
                }
            }
            .onFailure {
                showToast(it.message ?: "알 수 없는 오류가 발생했습니다.")
            }
    }

    private fun validateInput(): Result<String> {
        val nameValidation = binding.tilFoodtruckName.editText!!.text.toString().isNotBlank()
        val carNumValidation = binding.tilCarNumber.editText!!.text.toString().let {
            it.isNotBlank() && it.matches(Regex("^[0-9]{2,3}[가-힣][0-9]{4}$"))
        }
        val categoryValidation =
            menuCategoryAdapter?.getSelectedCategoryList()?.isNotEmpty() ?: false

        val msg = if (!nameValidation) {
            "이름을 입력해주세요"
        } else if (!carNumValidation) {
            "차량 번호를 올바르게 입력해주세요"
        } else if (!categoryValidation) {
            "카테고리를 선택해주세요"
        } else {
            return Result.success("success")
        }

        return Result.failure(Exception(msg))
    }

    private fun checkBackStackDialog() {
        MaterialAlertDialogBuilder(_activity)
            .setTitle("등록을 취소하시겠습니까?")
            .setMessage("작성 중인 내용이 모두 지워집니다.")
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
                    .apply(RequestOptions().circleCrop())
                    .into(binding.ivTruckPhoto)
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
                        .apply(RequestOptions().circleCrop())
                        .into(binding.ivTruckPhoto)
                }
            }
        }
    // endregion 이미지 관련 함수


    private val dummyCategory = listOf(
        "덮밥",
        "전기구이통닭",
        "꼬치",
        "타코야끼",
        "타코 & 케밥",
        "분식",
        "빵",
        "구황작물",
        "카페 & 디저트",
        "기타"
    )
}