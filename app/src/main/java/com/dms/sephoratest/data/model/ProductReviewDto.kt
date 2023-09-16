package com.dms.sephoratest.data.model

data class ProductReviewsDto(
    val product_id: Long,
    val hide: Boolean = false,
    val reviews: List<ReviewDto>
)

data class ReviewDto(
    val name: String? = null,
    val text: String? = null,
    val rating: Double?
)