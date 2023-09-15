package com.dms.sephoratest.data.repository

import com.dms.sephoratest.domain.model.Product
import com.dms.sephoratest.domain.model.ProductReview
import com.dms.sephoratest.domain.repository.ProductRepository

class ProductRepositoryImpl : ProductRepository {
    override suspend fun getProductsList(): List<Product> {
        TODO("Not yet implemented")
    }

    override suspend fun getProductsReview(): List<ProductReview> {
        TODO("Not yet implemented")
    }
}