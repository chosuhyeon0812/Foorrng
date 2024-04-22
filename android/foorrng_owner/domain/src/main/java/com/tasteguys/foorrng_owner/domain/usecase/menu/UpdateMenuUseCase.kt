package com.tasteguys.foorrng_owner.domain.usecase.menu

import com.tasteguys.foorrng_owner.domain.repository.MenuRepository
import java.io.File
import javax.inject.Inject

class UpdateMenuUseCase @Inject constructor(
    private val menuRepository: MenuRepository
) {
    suspend operator fun invoke(
        menuId: Long,
        name: String,
        price: Long,
        foodtruckId: Long,
        picture: File?
    ) = menuRepository.updateMenu(menuId, name, price, foodtruckId, picture)
}