package com.tasteguys.foorrng_customer.presentation.truck

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import com.tasteguys.foorrng_customer.presentation.databinding.DialogNavigationBinding


class NavDialog(
    private val context: Context,
    private val latitude: Double,
    private val longitude: Double,
    private val address: String
) : Dialog(context) {

    private var binding: DialogNavigationBinding =
        DialogNavigationBinding.inflate(LayoutInflater.from(context))


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 다이얼로그의 기본 배경색을 투명하게
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setListener()

    }

    private fun setListener() {
        callNaverMap()
    }

    private fun callNaverMap() {
        binding.layoutNaverMap.setOnClickListener {
            val url =
                "nmap://route/car?dlat=${latitude}&dlng=${longitude}&dname=${address}&appname=com.tasteguys.foorrng_owner"

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            intent.addCategory(Intent.CATEGORY_BROWSABLE)

            try {
                context.startActivity(intent)
            } catch (e: Exception) {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=com.nhn.android.nmap")
                    )
                );
            }

            this.dismiss()
        }
    }

}