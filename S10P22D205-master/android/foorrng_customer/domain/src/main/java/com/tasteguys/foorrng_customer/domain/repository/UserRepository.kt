package com.tasteguys.foorrng_customer.domain.repository

import com.tasteguys.foorrng_customer.domain.model.user.TokenData
import com.tasteguys.foorrng_customer.domain.model.user.UserData

interface UserRepository {
    suspend fun registerCustomer(
        userUid: Long,
        name: String,
        email: String
    ): Result<Long>

    suspend fun login(
        userUid: Long,
        name: String,
        email: String
    ): Result<TokenData>

    suspend fun getUser(): Result<UserData>
}