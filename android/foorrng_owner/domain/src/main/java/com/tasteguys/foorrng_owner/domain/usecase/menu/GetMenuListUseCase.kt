package com.tasteguys.foorrng_owner.domain.usecase.menu

import com.tasteguys.foorrng_owner.domain.repository.MenuRepository
import javax.inject.Inject

class GetMenuListUseCase @Inject constructor(
    private val menuRepository: MenuRepository
) {
    suspend operator fun invoke(foodtruckId: Long) = menuRepository.getMenuList(foodtruckId)
}