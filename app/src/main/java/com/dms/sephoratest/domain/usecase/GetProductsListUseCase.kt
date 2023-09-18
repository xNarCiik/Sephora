package com.dms.sephoratest.domain.usecase

import com.dms.sephoratest.domain.model.Product
import com.dms.sephoratest.domain.repository.ProductsRepository

class GetProductsListUseCase(
    private val productsRepository: ProductsRepository
) {
    suspend fun run(): List<Product> {
        return productsRepository.getProductsList()
    }
}