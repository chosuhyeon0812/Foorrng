package com.tasteguys.foorrng_customer.module

import com.tasteguys.foorrng_customer.data.repository.festival.FestivalRepositoryImpl
import com.tasteguys.foorrng_customer.data.repository.food.FoodCategoryRepositoryImpl
import com.tasteguys.foorrng_customer.data.repository.truck.TruckRepositoryImpl
import com.tasteguys.foorrng_customer.data.repository.user.UserRepositoryImpl
import com.tasteguys.foorrng_customer.domain.repository.FestivalRepository
import com.tasteguys.foorrng_customer.domain.repository.FoodCategoryRepository
import com.tasteguys.foorrng_customer.domain.repository.TruckRepository
import com.tasteguys.foorrng_customer.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Singleton
    @Binds
    abstract fun bindTruckRepository(truckRepositoryImpl: TruckRepositoryImpl): TruckRepository

    @Singleton
    @Binds
    abstract fun bindFoodCategoryRepository(foodCategoryRepositoryImpl: FoodCategoryRepositoryImpl): FoodCategoryRepository

    @Singleton
    @Binds
    abstract fun bindFestivalRepository(festivalRepositoryImpl: FestivalRepositoryImpl): FestivalRepository

}