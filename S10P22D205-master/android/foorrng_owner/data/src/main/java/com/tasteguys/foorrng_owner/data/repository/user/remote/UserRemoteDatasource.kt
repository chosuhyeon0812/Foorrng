package com.tasteguys.foorrng_owner.data.repository.user.remote

import com.tasteguys.foorrng_owner.data.model.user.LoginResponse

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

    suspend fun registBusinessNumber(
        businessNumber: String
    ): Result<String>
}