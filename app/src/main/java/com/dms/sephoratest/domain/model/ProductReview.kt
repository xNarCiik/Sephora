package com.dms.sephoratest.domain.model

data class ProductReview(
    val id: Long,
    val reviews: List<Review>
)

data class Review(
    val name: String? = null,
    val text: String,
    val rating: Double?
)