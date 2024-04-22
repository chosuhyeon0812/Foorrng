package com.tasteguys.foorrng_owner.presentation.base

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

abstract class BaseFragment<B : ViewBinding>(
    private val bind: (View) -> B,
    @LayoutRes layoutResId: Int
) : Fragment(layoutResId) {
    private var _binding: B? = null
    protected val binding get() = _binding!!
    protected lateinit var _activity: Context
    private lateinit var loadingDialog: LoadingDialog

    @SuppressLint("ResourceType")
    override fun onAttach(context: Context) {
        super.onAttach(context)
        _activity = context
        loadingDialog = LoadingDialog(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bind(super.onCreateView(inflater, container, savedInstanceState)!!)
        binding.root.setOnClickListener {
            hideKeyboard()
        }
        return binding.root
    }

    fun hideKeyboard(){
        val inputManager: InputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }


    override fun onDestroyView() {
        _binding = null
        hideLoading()
        super.onDestroyView()
    }

    fun showToast(message: String) {
        Toast.makeText(_activity, message, Toast.LENGTH_SHORT).show()
    }

    fun showSnackBar(message: String){
        Snackbar.make(binding.root,message,Snackbar.LENGTH_SHORT).show()
    }

    protected fun showLoading() {
        loadingDialog.showLoadingDialog()
    }

    protected fun hideLoading() {
        loadingDialog.hideLoadingDialog()
    }

}