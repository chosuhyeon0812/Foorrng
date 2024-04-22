package com.tasteguys.foorrng_customer.module

import android.content.Context
import com.tasteguys.foorrng_customer.presentation.base.GeoManager
import com.tasteguys.foorrng_customer.presentation.base.LocationManager
import com.tasteguys.foorrng_customer.presentation.base.PrefManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilModule {
    @Provides
    @Singleton
    fun providePrefManager(
        @ApplicationContext context: Context
    ): PrefManager {
        return PrefManager(context)
    }

    @Provides
    @Singleton
    fun provideGeoManager(
        @ApplicationContext context:Context
    ): GeoManager{
        return GeoManager(context)
    }

    @Provides
    @Singleton
    fun provideLocationManager(
        @ApplicationContext context: Context
    ): LocationManager{
        return LocationManager(context)
    }


}