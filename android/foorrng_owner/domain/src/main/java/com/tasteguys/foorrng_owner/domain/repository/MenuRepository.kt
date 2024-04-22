package com.tasteguys.foorrng_owner.domain.repository

import com.tasteguys.foorrng_owner.domain.model.menu.MenuData
import java.io.File

interface MenuRepository {
    suspend fun registMenu(
        name: String,
        price: Long,
        foodtruckId: Long,
        picture: File?,
    ): Result<MenuData>

    suspend fun updateMenu(
        menuId: Long,
        name: String,
        price: Long,
        foodtruckId: Long,
        picture: File?,
    ): Result<MenuData>

    suspend fun getMenuList(
        foodtruckId: Long,
    ): Result<List<MenuData>>
}