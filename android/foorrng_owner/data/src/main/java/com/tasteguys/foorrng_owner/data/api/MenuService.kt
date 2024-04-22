package com.tasteguys.foorrng_owner.data.api

import com.tasteguys.foorrng_owner.data.model.DefaultResponse
import com.tasteguys.foorrng_owner.data.model.menu.MenuRegistRequest
import com.tasteguys.foorrng_owner.data.model.menu.MenuResponse
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface MenuService {
    @POST("menu/regist")
    @Multipart
    suspend fun registMenu(
        @Part("menuRequestDto") menuRequestDto: MenuRegistRequest,
        @Part picture: MultipartBody.Part?,
    ) : Result<DefaultResponse<MenuResponse>>

    @PATCH("menu/{menuId}")
    @Multipart
    suspend fun updateMenu(
        @Path("menuId") menuId: Long,
        @Part("menuRequestDto") menuRequestDto: MenuRegistRequest,
        @Part picture: MultipartBody.Part?,
    ): Result<DefaultResponse<MenuResponse>>

    @GET("menu/{foodtruckId}")
    suspend fun getMenuList(
        @Path("foodtruckId") foodtruckId: Long,
    ): Result<DefaultResponse<List<MenuResponse>>>
}