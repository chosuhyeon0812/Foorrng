package com.tasteguys.foorrng_customer.module

import com.tasteguys.foorrng_customer.data.repository.festival.remote.FestivalRemoteDataSource
import com.tasteguys.foorrng_customer.data.repository.festival.remote.FestivalRemoteDataSourceImpl
import com.tasteguys.foorrng_customer.data.repository.food.FoodCategoryRepositoryImpl
import com.tasteguys.foorrng_customer.data.repository.food.local.FoodCategoryLocalDataSource
import com.tasteguys.foorrng_customer.data.repository.food.local.FoodCategoryLocalDataSourceImpl
import com.tasteguys.foorrng_customer.data.repository.food.remote.FoodCategoryRemoteDataSource
import com.tasteguys.foorrng_customer.data.repository.food.remote.FoodCategoryRemoteDataSourceImpl
import com.tasteguys.foorrng_customer.data.repository.truck.remote.TruckRemoteDatasource
import com.tasteguys.foorrng_customer.data.repository.truck.remote.TruckRemoteDatasourceImpl
import com.tasteguys.foorrng_customer.data.repository.user.remote.UserRemoteDatasource
import com.tasteguys.foorrng_customer.data.repository.user.remote.UserRemoteDatasourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Singleton
    @Binds
    abstract fun bindUserRemoteDataSource(userRemoteDataSourceImpl: UserRemoteDatasourceImpl): UserRemoteDatasource

    @Singleton
    @Binds
    abstract fun bindTruckRemoteDataSource(truckRemoteDataSourceImpl: TruckRemoteDatasourceImpl): TruckRemoteDatasource

    @Singleton
    @Binds
    abstract fun bindFoodCategoryLocalDataSource(foodCategoryLocalDataSourceImpl: FoodCategoryLocalDataSourceImpl): FoodCategoryLocalDataSource

    @Singleton
    @Binds
    abstract fun bindFoodCategoryRemoteDataSource(foodCategoryRemoteDataSourceImpl: FoodCategoryRemoteDataSourceImpl): FoodCategoryRemoteDataSource

    @Singleton
    @Binds
    abstract fun bindFestivalRemoteDataSource(festivalRemoteDataSourceImpl: FestivalRemoteDataSourceImpl): FestivalRemoteDataSource

}