package com.dms.sephoratest.data.model.mapper

import com.dms.sephoratest.data.model.ProductReviewDto
import com.dms.sephoratest.data.model.ReviewDto
import com.dms.sephoratest.domain.model.ProductReview
import com.dms.sephoratest.domain.model.Review

internal fun ProductReviewDto.toDomain(): ProductReview {
    return ProductReview(
        id = product_id,
        reviews = reviews.map { it.toDomain() }
    )
}

internal fun ReviewDto.toDomain(): Review {
    return Review(
        name = name,
        text = text,
        rating = rating
    )
}