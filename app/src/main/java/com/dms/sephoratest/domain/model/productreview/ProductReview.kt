package com.dms.sephoratest.domain.model.productreview

data class ProductReview(
    val product_id: Long,
    val hide: Boolean = false,
    val reviews: List<Review>
)

data class Review(
    val name: String? = null,
    val text: String,
    val rating: Double?
)