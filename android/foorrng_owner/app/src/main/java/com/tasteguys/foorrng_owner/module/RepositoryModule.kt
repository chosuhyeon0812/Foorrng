package com.tasteguys.foorrng_owner.module

import com.tasteguys.foorrng_owner.data.repository.article.ArticleRepositoryImpl
import com.tasteguys.foorrng_owner.data.repository.foodtruck.FoodtruckRepositoryImpl
import com.tasteguys.foorrng_owner.data.repository.menu.MenuRepositoryImpl
import com.tasteguys.foorrng_owner.data.repository.recommend.RecommendRepositoryImpl
import com.tasteguys.foorrng_owner.data.repository.user.UserRepositpryImpl
import com.tasteguys.foorrng_owner.domain.repository.ArticleRepository
import com.tasteguys.foorrng_owner.domain.repository.FoodtruckRepository
import com.tasteguys.foorrng_owner.domain.repository.MenuRepository
import com.tasteguys.foorrng_owner.domain.repository.RecommendRepository
import com.tasteguys.foorrng_owner.domain.repository.UserRepository
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
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositpryImpl): UserRepository

    @Singleton
    @Binds
    abstract fun bindFoodtruckRepository(foodtruckRepositoryImpl: FoodtruckRepositoryImpl): FoodtruckRepository

    @Singleton
    @Binds
    abstract fun bindMenuRepository(menuRepositoryImpl: MenuRepositoryImpl): MenuRepository

    @Singleton
    @Binds
    abstract fun bindArticleRepository(articleRepositoryImpl: ArticleRepositoryImpl): ArticleRepository

    @Singleton
    @Binds
    abstract fun bindRecommendRepository(recommendRepositoryImpl: RecommendRepositoryImpl): RecommendRepository
}