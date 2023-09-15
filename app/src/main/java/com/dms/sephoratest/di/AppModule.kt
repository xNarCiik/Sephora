package com.dms.sephoratest.di

import com.dms.sephoratest.data.api.ProductsApi
import com.dms.sephoratest.data.api.RetrofitInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideProductsApi(): ProductsApi {
        return RetrofitInstance.productsApi
    }

}