package com.dms.sephoratest.data.model.mapper

import com.dms.sephoratest.data.model.ProductReviewsDto
import com.dms.sephoratest.data.model.ReviewDto
import com.dms.sephoratest.domain.model.ProductReviews
import com.dms.sephoratest.domain.model.Review

internal fun ProductReviewsDto.toDomain(): ProductReviews {
    return ProductReviews(
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