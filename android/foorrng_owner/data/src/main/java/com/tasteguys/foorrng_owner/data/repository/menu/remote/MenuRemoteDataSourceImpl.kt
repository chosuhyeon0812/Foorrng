package com.tasteguys.foorrng_owner.data.repository.menu.remote

import com.tasteguys.foorrng_owner.data.api.MenuService
import com.tasteguys.foorrng_owner.data.mapper.toNonDefault
import com.tasteguys.foorrng_owner.data.model.menu.MenuRegistRequest
import com.tasteguys.foorrng_owner.data.model.menu.MenuResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class MenuRemoteDataSourceImpl @Inject constructor(
    private val menuService: MenuService
) : MenuRemoteDataSource{
    override suspend fun registMenu(
        name: String,
        price: Long,
        foodtruckId: Long,
        picture: File?
    ): Result<MenuResponse> {
        val image = picture?.asRequestBody("image/jpg".toMediaTypeOrNull())
        val multipartImage = image?.let {
            MultipartBody.Part.createFormData("picture", picture.name, it)
        }
        val request = MenuRegistRequest(
            name = name,
            price = price,
            foodtruckId = foodtruckId
        )

        return menuService.registMenu(request, multipartImage)
            .toNonDefault()
    }

    override suspend fun updateMenu(
        menuId: Long,
        name: String,
        price: Long,
        foodtruckId: Long,
        picture: File?
    ): Result<MenuResponse> {
        val image = picture?.asRequestBody("image/jpg".toMediaTypeOrNull())
        val multipartImage = image?.let {
            MultipartBody.Part.createFormData("picture", picture.name, it)
        }
        val request = MenuRegistRequest(
            name = name,
            price = price,
            foodtruckId = foodtruckId
        )

        return menuService.updateMenu(menuId,request, multipartImage)
            .toNonDefault()
    }

    override suspend fun getMenuList(foodtruckId: Long): Result<List<MenuResponse>> {
        return  menuService.getMenuList(foodtruckId)
            .toNonDefault()
    }
}