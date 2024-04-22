package com.tasteguys.foorrng_customer.presentation.main

import android.view.MenuItem

class MainToolbarControl(
    val visible: Boolean = false,
    val title: String = "",
    val menuRes: Int = 0,
    val backIcon: Boolean = true,
    val menuList: List<Int> = listOf()
) {
    private var _menuItemClickListener: (MenuItem) -> (Unit) = {}
    val menuItemClickListener: (MenuItem) -> (Unit)
        get() = _menuItemClickListener

    private var _navIconClickListener: () -> (Unit) = {}
    val navIconClickListener: () -> (Unit)
        get() = _navIconClickListener

    fun addMenuItemClickListener(listener: (MenuItem) -> (Unit)) : MainToolbarControl {
        _menuItemClickListener = listener
        return this
    }

    fun addNavIconClickListener(listener: () -> (Unit)): MainToolbarControl {
        _navIconClickListener = listener
        return this
    }
}
