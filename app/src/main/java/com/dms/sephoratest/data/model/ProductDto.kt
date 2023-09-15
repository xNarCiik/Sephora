package com.dms.sephoratest.data.model

data class ProductDto(
    val product_id: Long,
    val product_name: String,
    val description: String,
    val price: Double,
    val images_url: ImagesUrl,
    val c_brand: Brand,
    val is_productSet: Boolean,
    val is_special_brand: Boolean
)

data class ImagesUrl(
    val small: String,
    val large: String
)

data class Brand(
    val id: String,
    val name: String
)