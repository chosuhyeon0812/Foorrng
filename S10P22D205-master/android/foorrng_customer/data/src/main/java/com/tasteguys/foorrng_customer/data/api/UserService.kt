package com.tasteguys.foorrng_customer.data.api

import com.tasteguys.foorrng_customer.data.model.DefaultResponse
import com.tasteguys.foorrng_customer.data.model.user.LoginResponse
import com.tasteguys.foorrng_customer.data.model.user.UserRequest
import com.tasteguys.foorrng_customer.data.model.user.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {
    @POST("user/regist/user")
    suspend fun registerCustomer(
        @Body userRequest: UserRequest
    ): Result<DefaultResponse<Long>>

    @POST("user/login")
    suspend fun login(
        @Body userRequest: UserRequest
    ): Result<DefaultResponse<LoginResponse>>

    @GET("user")
    suspend fun getUserData():Result<DefaultResponse<UserResponse>>

}