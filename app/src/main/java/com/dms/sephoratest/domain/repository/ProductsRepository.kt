package com.dms.sephoratest.domain.repository

import com.dms.sephoratest.domain.model.Product
import com.dms.sephoratest.domain.model.ProductReviews

interface ProductsRepository {
    suspend fun getProductsList(): List<Product>
    suspend fun getProductReviews(): List<ProductReviews>
}
