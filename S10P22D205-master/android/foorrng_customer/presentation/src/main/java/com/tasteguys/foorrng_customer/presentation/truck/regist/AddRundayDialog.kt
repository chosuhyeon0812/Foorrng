package com.tasteguys.foorrng_customer.presentation.truck.regist

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.tasteguys.foorrng_customer.presentation.databinding.DialogAddRundayBinding
import com.tasteguys.foorrng_customer.presentation.model.RunDay
import java.time.DayOfWeek

private const val TAG = "AddRundayDialog"
class AddRundayDialog(
    private val context: Context,
    private val selectedDay: DayOfWeek,
) :
    Dialog(context) {

    private var binding: DialogAddRundayBinding = DialogAddRundayBinding.inflate(LayoutInflater.from(context))
    private var cancelListener: (dialog: Dialog) -> Unit = { _ -> }
    private var registListener: (dialog: Dialog, RunDay) -> Unit = { _, _ -> }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 다이얼로그의 기본 배경색을 투명하게
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setNumberPicker()
        applyCreateListener()
        applyCancleListener()
    }

    private fun applyCancleListener() {
        binding.tvCancel.setOnClickListener {
            cancelListener(this)
        }
    }

    private fun applyCreateListener() {
        binding.tvRegist.setOnClickListener {
            Log.d(TAG, "applyCreateListener: ${binding.npStartTime.value}")
            val startTime = "${getTimeString(binding.npStartTime.value)}:${getTimeString(binding.npStartMinute.value)}"
            val endTime = "${getTimeString(binding.npEndTime.value)}:${getTimeString(binding.npEndMinute.value)}"
            Log.d(TAG, "applyCreateListener: $startTime $endTime")
            registListener(this, RunDay(selectedDay,startTime,endTime))
        }
    }


    fun setCancelListener(cancelListener: (dialog: Dialog) -> Unit): AddRundayDialog{
        this.cancelListener = cancelListener
        applyCancleListener()
        return this
    }

    fun setCreateListener(registListener: (dialog: Dialog, runDay: RunDay) -> Unit): AddRundayDialog{
        this.registListener = registListener
        applyCreateListener()
        return this
    }

    private fun setNumberPicker() {
        with(binding.npStartTime){
            minValue = 0
            maxValue = 23
        }
        with(binding.npStartMinute){
            minValue = 0
            maxValue = 59
        }
        with(binding.npEndTime){
            minValue = 0
            maxValue = 23
        }
        with(binding.npEndMinute){
            minValue = 0
            maxValue = 59
        }
    }

    private fun getTimeString(number: Int): String{
        return if (number < 10){
            "0$number"
        } else {
            number.toString()
        }
    }
}