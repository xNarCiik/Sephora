package com.dms.sephoratest.data.repository

import com.dms.sephoratest.data.api.ProductsApi
import com.dms.sephoratest.domain.model.Product
import com.dms.sephoratest.domain.model.ProductReviews
import com.dms.sephoratest.domain.repository.ProductsRepository

class ProductsRepositoryImpl(private val productsApi: ProductsApi) : ProductsRepository {
    override suspend fun getProductsList(): List<Product> {
        TODO("Not yet implemented")
    }

    override suspend fun getProductReviews(): List<ProductReviews> {
        TODO("Not yet implemented")
    }
}