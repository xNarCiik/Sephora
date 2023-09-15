package com.dms.sephoratest.domain.repository

import com.dms.sephoratest.domain.model.Product
import com.dms.sephoratest.domain.model.ProductReview

interface ProductRepository {
    suspend fun getProductsList(): List<Product>
    suspend fun getProductsReview(): List<ProductReview>
}
