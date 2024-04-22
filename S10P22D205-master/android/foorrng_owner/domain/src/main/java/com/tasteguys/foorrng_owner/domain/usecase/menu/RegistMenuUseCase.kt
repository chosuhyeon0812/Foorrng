package com.tasteguys.foorrng_owner.domain.usecase.menu

import com.tasteguys.foorrng_owner.domain.repository.MenuRepository
import java.io.File
import javax.inject.Inject

class RegistMenuUseCase @Inject constructor(
    private val menuRepository: MenuRepository
) {
    suspend operator fun invoke(
        name: String,
        price: Long,
        foodtruckId: Long,
        picture: File?
    ) = menuRepository.registMenu(name, price, foodtruckId, picture)
}