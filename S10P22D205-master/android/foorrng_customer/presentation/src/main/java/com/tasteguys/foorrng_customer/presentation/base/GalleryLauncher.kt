package com.tasteguys.foorrng_customer.presentation.base

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

class GalleryLauncher(fragment: Fragment) {
    fun openGallery(){
        pickImageLauncher.launch(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI))
    }


    // 가져온 사진 보여주기
    private val pickImageLauncher: ActivityResultLauncher<Intent> = fragment.registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            data?.data?.let {
                pictureCallbackListener.onGetData(it)
            }
        }
    }

    interface PictureCallbackListener{
        fun onGetData(data: Uri)
    }

    lateinit var pictureCallbackListener: PictureCallbackListener


}