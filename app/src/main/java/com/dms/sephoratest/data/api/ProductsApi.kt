package com.dms.sephoratest.data.api

import com.dms.sephoratest.data.model.ProductDto
import com.dms.sephoratest.data.model.ProductReviewDto
import retrofit2.http.GET

interface ProductsApi {

    @GET("items.json")
    suspend fun getProductsList(): Array<ProductDto>

    @GET("reviews.json")
    suspend fun getProductReview(): Array<ProductReviewDto>
}