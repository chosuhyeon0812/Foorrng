package com.tasteguys.foorrng_customer.presentation.main

import android.util.Log
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import com.tasteguys.foorrng_customer.presentation.base.BaseFragment
import com.tasteguys.foorrng_customer.presentation.base.IToolbarFragment

private const val TAG = "MainBaseFragment"
abstract class MainBaseFragment<B : ViewBinding>(
    private val bind: (View) -> B,
    @LayoutRes layoutResId: Int
) : BaseFragment<B>(
    bind,layoutResId
), IToolbarFragment {
    protected val mainViewModel: MainViewModel by activityViewModels()

    override fun setToolbar() {
        mainViewModel.changeToolbar(MainToolbarControl())
    }

    override fun onStart() {
        super.onStart()
        setToolbar()
    }

    override fun onStop() {
        super.onStop()
        mainViewModel.changeToolbar(MainToolbarControl())
    }
}