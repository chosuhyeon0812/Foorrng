package com.tasteguys.foorrng_customer.presentation.truck

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun showNavDialog(context: Context, address: String, latitude: Double, longitude: Double) {
    MaterialAlertDialogBuilder(context)
        .setTitle("길찾기")
        .setMessage(
            if (address.isNotBlank()) {
                "$address\n로 가기위해 네이버지도앱을 실행합니다."
            } else {
                "네이버지도앱을 실행합니다."
            }
        )
        .setPositiveButton("확인"){_,_ ->
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
        }
        .setNegativeButton("취소"){_,_ ->

        }
        .show()
}