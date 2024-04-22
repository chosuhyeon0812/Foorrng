package com.tasteguys.foorrng_owner.data.repository.user.remote

import com.tasteguys.foorrng_owner.data.api.UserService
import com.tasteguys.foorrng_owner.data.mapper.toNonDefault
import com.tasteguys.foorrng_owner.data.model.user.LoginResponse
import com.tasteguys.foorrng_owner.data.model.user.RegistBusinessNumberRequest
import com.tasteguys.foorrng_owner.data.model.user.UserRequest
import javax.inject.Inject

class UserRemoteDatasourceImpl @Inject constructor(
    private val userService: UserService
) : UserRemoteDatasource{
    override suspend fun registerOwner(userUid: Long, name: String, email: String): Result<Long> {
        return userService.registerOwner(
            UserRequest(
                userUid,
                name,
                email
            )
        ).toNonDefault()
    }

    override suspend fun login(userUid: Long, name: String, email: String): Result<LoginResponse> {
        return userService.login(UserRequest(
            userUid,
            name,
            email
        )).toNonDefault()
    }

    override suspend fun registBusinessNumber(businessNumber: String): Result<String> {
        return userService.registBusinessNumber(RegistBusinessNumberRequest(
            businessNumber
        )).toNonDefault()
    }
}