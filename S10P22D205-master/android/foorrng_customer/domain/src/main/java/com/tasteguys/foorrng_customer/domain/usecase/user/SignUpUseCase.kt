package com.tasteguys.foorrng_customer.domain.usecase.user

import com.tasteguys.foorrng_customer.domain.repository.UserRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        userUid: Long,
        name: String,
        email: String
    ): Result<Long> {
        return userRepository.registerCustomer(userUid, name, email)
    }
}