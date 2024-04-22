package com.tasteguys.foorrng_owner.data.repository.user

import com.tasteguys.foorrng_owner.data.mapper.toDomain
import com.tasteguys.foorrng_owner.data.repository.user.remote.UserRemoteDatasource
import com.tasteguys.foorrng_owner.domain.model.user.LoginData
import com.tasteguys.foorrng_owner.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositpryImpl @Inject constructor(
    private val userRemoteDatasource: UserRemoteDatasource
): UserRepository {
    override suspend fun registerOwner(userUid: Long, name: String, email: String): Result<Long> {
        return userRemoteDatasource.registerOwner(userUid, name, email)
    }

    override suspend fun login(userUid: Long, name: String, email: String): Result<LoginData> {
        return userRemoteDatasource.login(userUid, name, email).map {
            it.toDomain()
        }
    }

    override suspend fun registBusinessNumber(businessNumber: String): Result<String> {
        return userRemoteDatasource.registBusinessNumber(businessNumber)
    }
}