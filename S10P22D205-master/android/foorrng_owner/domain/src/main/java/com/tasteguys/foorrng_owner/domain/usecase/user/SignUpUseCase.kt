package com.tasteguys.foorrng_owner.domain.usecase.user

import com.tasteguys.foorrng_owner.domain.repository.UserRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        userUid: Long,
        name: String,
        email: String
    ): Result<Long> {
        return userRepository.registerOwner(userUid, name, email)
    }
}