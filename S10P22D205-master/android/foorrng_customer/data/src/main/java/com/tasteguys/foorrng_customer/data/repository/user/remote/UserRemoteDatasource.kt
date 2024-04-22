package com.tasteguys.foorrng_customer.data.repository.user.remote

import com.tasteguys.foorrng_customer.data.model.user.LoginResponse
import com.tasteguys.foorrng_customer.data.model.user.UserResponse

interface UserRemoteDatasource {
    suspend fun registerOwner(
        userUid: Long,
        name: String,
        email: String
    ): Result<Long>

    suspend fun login(
        userUid: Long,
        name: String,
        email: String
    ): Result<LoginResponse>

    suspend fun getUser(): Result<UserResponse>

}