package com.tasteguys.foorrng_customer.module

import android.content.Context
import androidx.room.Room
import com.tasteguys.foorrng_customer.data.FoorrngCustomerDataBase
import com.tasteguys.foorrng_customer.data.dao.FoodCategoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    @Singleton
    fun providesFoorrngCustomerDatabase(
        @ApplicationContext context: Context
    ): FoorrngCustomerDataBase {
        return Room.databaseBuilder(
            context,
            FoorrngCustomerDataBase::class.java,
            "foorrng_cus_db"
        ).build()
    }

    @Provides
    @Singleton
    fun providesFoodCategoryDao(
        foorrngCustomerDataBase: FoorrngCustomerDataBase
    ): FoodCategoryDao{
        return foorrngCustomerDataBase.foodCategoryDao()
    }

}