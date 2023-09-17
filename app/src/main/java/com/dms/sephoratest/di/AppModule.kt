package com.dms.sephoratest.di

import com.dms.sephoratest.data.api.ProductsApi
import com.dms.sephoratest.data.api.RetrofitInstance
import com.dms.sephoratest.data.repository.ProductsRepositoryImpl
import com.dms.sephoratest.domain.repository.ProductsRepository
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

    @Provides
    @Singleton
    fun provideProductsRepository(): ProductsRepository {
        return ProductsRepositoryImpl(productsApi = provideProductsApi())
    }
}