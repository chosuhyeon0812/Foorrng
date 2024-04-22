package com.tasteguys.foorrng_customer.domain.usecase.user

import com.tasteguys.foorrng_customer.domain.repository.UserRepository
import javax.inject.Inject

class GetUserDataUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke() = userRepository.getUser()
}