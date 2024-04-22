package com.tasteguys.foorrng_customer.data.repository.user

import com.tasteguys.foorrng_customer.data.mapper.toDomain
import com.tasteguys.foorrng_customer.data.repository.user.remote.UserRemoteDatasource
import com.tasteguys.foorrng_customer.domain.model.user.TokenData
import com.tasteguys.foorrng_customer.domain.model.user.UserData
import com.tasteguys.foorrng_customer.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDatasource: UserRemoteDatasource
): UserRepository {
    override suspend fun registerCustomer(
        userUid: Long,
        name: String,
        email: String
    ): Result<Long> {
        return userRemoteDatasource.registerOwner(userUid, name, email)
    }

    override suspend fun login(userUid: Long, name: String, email: String): Result<TokenData> {
        return userRemoteDatasource.login(userUid, name, email).map {
            it.toDomain()
        }
    }

    override suspend fun getUser(): Result<UserData> {
        return userRemoteDatasource.getUser().map {
            it.toDomain()
        }
    }

}