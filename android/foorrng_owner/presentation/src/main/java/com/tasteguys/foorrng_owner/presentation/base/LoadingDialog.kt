package com.tasteguys.foorrng_owner.presentation.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.tasteguys.foorrng_owner.presentation.R

class LoadingDialog(context: Context) : Dialog(context) {
    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_loading)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCancelable(false)
    }

    fun showLoadingDialog() {
        if (!isShowing) {
            show()
        }
    }

    fun hideLoadingDialog() {
        if (isShowing) {
            dismiss()
        }
    }
}