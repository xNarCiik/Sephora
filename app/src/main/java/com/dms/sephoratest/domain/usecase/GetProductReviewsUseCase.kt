package com.dms.sephoratest.domain.usecase

import com.dms.sephoratest.domain.model.ProductReviews
import com.dms.sephoratest.domain.repository.ProductsRepository

class GetProductReviewsUseCase(
    private val productsRepository: ProductsRepository
) {
    suspend fun run(): List<ProductReviews> {
        return productsRepository.getProductReviews()
    }
}