package com.tasteguys.foorrng_owner.data.repository.menu

import com.tasteguys.foorrng_owner.data.mapper.toMenuData
import com.tasteguys.foorrng_owner.data.repository.menu.remote.MenuRemoteDataSource
import com.tasteguys.foorrng_owner.domain.model.menu.MenuData
import com.tasteguys.foorrng_owner.domain.repository.MenuRepository
import java.io.File
import javax.inject.Inject

class MenuRepositoryImpl @Inject constructor(
    private val menuRemoteDataSource: MenuRemoteDataSource
) : MenuRepository {
    override suspend fun registMenu(
        name: String,
        price: Long,
        foodtruckId: Long,
        picture: File?
    ): Result<MenuData> {
        return menuRemoteDataSource.registMenu(name, price, foodtruckId, picture).map {
            it.toMenuData()
        }
    }

    override suspend fun updateMenu(
        menuId: Long,
        name: String,
        price: Long,
        foodtruckId: Long,
        picture: File?
    ): Result<MenuData> {
        return menuRemoteDataSource.updateMenu(menuId, name, price, foodtruckId, picture).map {
            it.toMenuData()
        }
    }

    override suspend fun getMenuList(foodtruckId: Long): Result<List<MenuData>> {
        return menuRemoteDataSource.getMenuList(foodtruckId).map {
            it.map { menuResponse ->
                menuResponse.toMenuData()
            }
        }
    }
}