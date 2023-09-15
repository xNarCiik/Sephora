package com.dms.sephoratest.data.api

import com.dms.sephoratest.data.model.ProductDto
import retrofit2.http.GET

interface ProductsApi {

    @GET("items.json")
    suspend fun getProductsList(): Array<ProductDto>

}