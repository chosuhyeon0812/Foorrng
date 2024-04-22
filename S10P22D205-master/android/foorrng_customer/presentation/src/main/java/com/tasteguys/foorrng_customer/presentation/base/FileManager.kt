package com.tasteguys.foorrng_customer.presentation.base

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

private const val TAG = "FileManager"
fun Uri?.toFile(context: Context): File? {
    return if(this != null){
        val proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        val c: Cursor? = context.contentResolver.query(this, proj, null, null, null)
        val index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()
        val result = c?.getString(index!!)
        File(result!!)
    }else{
        null
    }
}

fun Long.toDateString(): String{
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return SimpleDateFormat("yyyy.MM.dd", Locale.KOREAN).format(calendar.time).toString()
}

class FileManager {
    companion object{

    }
}