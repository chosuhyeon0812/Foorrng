package com.tasteguys.foorrng_customer.data.repository.user.remote

import com.tasteguys.foorrng_customer.data.api.UserService
import com.tasteguys.foorrng_customer.data.mapper.toNonDefault
import com.tasteguys.foorrng_customer.data.model.user.LoginResponse
import com.tasteguys.foorrng_customer.data.model.user.UserRequest
import com.tasteguys.foorrng_customer.data.model.user.UserResponse
import javax.inject.Inject

class UserRemoteDatasourceImpl @Inject constructor(
    private val userService: UserService
) : UserRemoteDatasource{
    override suspend fun registerOwner(userUid: Long, name: String, email: String): Result<Long> {
        return userService.registerCustomer(
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

    override suspend fun getUser(): Result<UserResponse> {
        return userService.getUserData().toNonDefault()
    }
}