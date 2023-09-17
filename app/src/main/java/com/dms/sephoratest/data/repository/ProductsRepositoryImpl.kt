package com.dms.sephoratest.data.repository

import com.dms.sephoratest.data.api.ProductsApi
import com.dms.sephoratest.data.model.mapper.toDomain
import com.dms.sephoratest.domain.model.Product
import com.dms.sephoratest.domain.model.ProductReviews
import com.dms.sephoratest.domain.repository.ProductsRepository

class ProductsRepositoryImpl(private val productsApi: ProductsApi) : ProductsRepository {
    override suspend fun getProductsList(): List<Product> {
        val responseProductsList = productsApi.getProductsList()
        if (responseProductsList.isSuccessful) {
            responseProductsList.body()?.let { productsList ->
                // Transform ProductsDto list to Products list
                return productsList.map { it.toDomain() }
            }
            return arrayListOf()
        } else {
            throw NetworkErrorException(message = responseProductsList.errorBody()?.string())
        }
    }

    override suspend fun getProductReviews(): List<ProductReviews> {
        val responseProductReviews = productsApi.getProductReviews()
        if (responseProductReviews.isSuccessful) {
            responseProductReviews.body()?.let { productReviews ->
                // Transform ProductReviewDto list to ProductReviews list
                return productReviews.map { it.toDomain() }
            }
            return arrayListOf()
        } else {
            throw NetworkErrorException(message = responseProductReviews.errorBody()?.string())
        }
    }
}

class NetworkErrorException(message: String?) : Exception(message)