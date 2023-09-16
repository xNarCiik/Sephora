package com.dms.sephoratest.domain.model

data class ProductReviews(
    val id: Long,
    val reviews: List<Review>
)

data class Review(
    val name: String? = null,
    val text: String? = null,
    val rating: Double?
)