package com.tasteguys.foorrng_customer.module

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tasteguys.foorrng_customer.AuthInterceptor
import com.tasteguys.foorrng_customer.data.api.FestivalService
import com.tasteguys.foorrng_customer.data.api.FoodService
import com.tasteguys.foorrng_customer.data.api.FoodTruckService
import com.tasteguys.foorrng_customer.data.api.UserService
import com.tasteguys.foorrng_owner.data.BuildConfig.BASE_URL
import com.tasteguys.retrofit_adapter.NetworkResponseAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
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
    fun providesUserService(
        retrofit: Retrofit
    ): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    @Singleton
    fun providesFoodTruckReportService(
        retrofit: Retrofit
    ): FoodTruckService {
        return retrofit.create(FoodTruckService::class.java)
    }

    @Provides
    @Singleton
    fun providesFoodService(
        retrofit: Retrofit
    ): FoodService{
        return retrofit.create(FoodService::class.java)
    }

    @Provides
    @Singleton
    fun providesFestivalService(
        retrofit: Retrofit
    ): FestivalService{
        return retrofit.create(FestivalService::class.java)
    }

}