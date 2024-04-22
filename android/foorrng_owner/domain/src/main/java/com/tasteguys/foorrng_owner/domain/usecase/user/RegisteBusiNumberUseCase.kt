package com.tasteguys.foorrng_owner.domain.usecase.user

import com.tasteguys.foorrng_owner.domain.repository.UserRepository
import javax.inject.Inject

class RegisteBusiNumberUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(businessNumber: String) = userRepository.registBusinessNumber(businessNumber)
}