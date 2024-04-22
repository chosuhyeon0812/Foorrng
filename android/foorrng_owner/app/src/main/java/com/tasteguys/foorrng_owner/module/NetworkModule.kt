package com.tasteguys.foorrng_owner.module


import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tasteguys.foorrng_owner.AuthInterceptor
import com.tasteguys.foorrng_owner.data.api.ApiClient.BASE_URL
import com.tasteguys.foorrng_owner.data.api.ArticleService
import com.tasteguys.foorrng_owner.data.api.FoodtruckService
import com.tasteguys.foorrng_owner.data.api.MenuService
import com.tasteguys.foorrng_owner.data.api.RecommendService
import com.tasteguys.foorrng_owner.data.api.UserService
import com.tasteguys.retrofit_adapter.NetworkResponseAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AuthRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AuthUserService

    @Provides
    @Singleton
    fun moshi() = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun providesRetrofitClient(
        moshi: Moshi,
        authInterceptor: AuthInterceptor,
//        authenticator: AuthAuthenticator
    ): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
//            .authenticator(authenticator)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    @AuthRetrofit
    fun providesAuthRetrofitClient(
        moshi: Moshi
    ): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    @AuthUserService
    fun providesAuthUserService(
        @AuthRetrofit retrofit: Retrofit
    ): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    @Singleton
    fun providesUserService(
        retrofit: Retrofit
    ): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    @Singleton
    fun providesFoodtruckService(
        retrofit: Retrofit
    ): FoodtruckService {
        return retrofit.create(FoodtruckService::class.java)
    }

    @Provides
    @Singleton
    fun providesMenuService(
        retrofit: Retrofit
    ): MenuService {
        return retrofit.create(MenuService::class.java)
    }

    @Provides
    @Singleton
    fun providesArticleService(
        retrofit: Retrofit
    ): ArticleService {
        return retrofit.create(ArticleService::class.java)
    }

    @Provides
    @Singleton
    fun providesRecommendService(
        retrofit: Retrofit
    ): RecommendService {
        return retrofit.create(RecommendService::class.java)
    }

}