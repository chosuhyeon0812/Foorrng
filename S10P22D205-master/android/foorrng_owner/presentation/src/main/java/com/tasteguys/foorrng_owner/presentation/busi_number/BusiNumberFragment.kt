package com.tasteguys.foorrng_owner.presentation.busi_number

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.tasteguys.foorrng_owner.presentation.R
import com.tasteguys.foorrng_owner.presentation.databinding.FragmentBusiNumberBinding
import com.tasteguys.foorrng_owner.presentation.home.HomeFragment
import com.tasteguys.foorrng_owner.presentation.main.MainBaseFragment
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "BusiNumberFragment_poorrng"
@AndroidEntryPoint
class BusiNumberFragment : MainBaseFragment<FragmentBusiNumberBinding>(
    FragmentBusiNumberBinding::bind, R.layout.fragment_busi_number
) {

    private val busiNumberViewModel: BusiNumberViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSubmit.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.layout_main_fragment, HomeFragment())
                .commit()
        }

        registerListener()
        registerObseve()
    }

    private fun registerListener(){
        binding.btnSubmit.setOnClickListener {
            binding.tilBusinessNumber.editText!!.text.let {
                if (it.length < 10) {
                    Log.d(TAG, "registerListener: ${it}")
                    showSnackBar("사업자 번호 10자리를 입력해주세요.")
                } else {
                    busiNumberViewModel.registBusinessNumber(it.toString())
                }
            }
        }
    }

    private fun registerObseve(){
        busiNumberViewModel.registResult.observe(viewLifecycleOwner){
            if (it.isSuccess){
                showToast("사업자 등록이 완료되었습니다.")
                parentFragmentManager.beginTransaction()
                    .replace(R.id.layout_main_fragment, HomeFragment())
                    .commit()
            } else {
                showToast(it.exceptionOrNull()?.message ?: "알 수 없는 오류가 발생했습니다.")
            }
        }
    }
}