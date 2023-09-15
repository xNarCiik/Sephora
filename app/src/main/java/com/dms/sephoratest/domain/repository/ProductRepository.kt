package com.dms.sephoratest.domain.repository

import com.dms.sephoratest.domain.model.product.Product
import com.dms.sephoratest.domain.model.productreview.ProductReview

interface ProductRepository {
    suspend fun getProductsList(): List<Product>
    suspend fun getProductsReview(): List<ProductReview>
}
