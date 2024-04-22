package com.tasteguys.foorrng_owner.data.repository.menu.remote

import com.tasteguys.foorrng_owner.data.model.menu.MenuResponse
import java.io.File

interface MenuRemoteDataSource {
    suspend fun registMenu(
        name: String,
        price: Long,
        foodtruckId: Long,
        picture: File?,
    ): Result<MenuResponse>

    suspend fun updateMenu(
        menuId: Long,
        name: String,
        price: Long,
        foodtruckId: Long,
        picture: File?,
    ): Result<MenuResponse>

    suspend fun getMenuList(
        foodtruckId: Long,
    ): Result<List<MenuResponse>>
}