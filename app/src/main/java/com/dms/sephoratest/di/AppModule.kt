package com.dms.sephoratest.di

import com.dms.sephoratest.data.api.ProductsApi
import com.dms.sephoratest.data.api.RetrofitInstance
import com.dms.sephoratest.data.repository.ProductsRepositoryImpl
import com.dms.sephoratest.domain.repository.ProductsRepository
import com.dms.sephoratest.domain.usecase.GetProductReviewsUseCase
import com.dms.sephoratest.domain.usecase.GetProductsListUseCase
import com.dms.sephoratest.presentation.MainViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Api Region
    @Provides
    @Singleton
    fun provideProductsApi(): ProductsApi = RetrofitInstance.productsApi
    // End Api region

    // Repository region
    @Provides
    @Singleton
    fun provideProductsRepository(productsApi: ProductsApi): ProductsRepository =
        ProductsRepositoryImpl(productsApi = productsApi)
    // End Repository region

    // View model region
    @Provides
    @Singleton
    fun provideMainViewModel(
        getProductsListUseCase: GetProductsListUseCase,
        getProductReviewsUseCase: GetProductReviewsUseCase
    ): MainViewModel = MainViewModel(
        getProductsListUseCase = getProductsListUseCase,
        getProductReviewsUseCase = getProductReviewsUseCase,
        dispatcher = Dispatchers.IO
    )
    // End view model region

    // Uses case region
    @Provides
    @Singleton
    fun provideGetProductsListUseCase(productsRepository: ProductsRepository): GetProductsListUseCase =
        GetProductsListUseCase(productsRepository = productsRepository)

    @Provides
    @Singleton
    fun provideGetProductReviewsUseCase(productsRepository: ProductsRepository): GetProductReviewsUseCase =
        GetProductReviewsUseCase(productsRepository = productsRepository)
    // End uses case region
}