package com.dms.sephoratest

import com.dms.sephoratest.domain.model.Product
import com.dms.sephoratest.domain.model.ProductReviews
import com.dms.sephoratest.domain.model.Review

var fakeProducts = arrayListOf(
    Product(
        id = 0L,
        name = "product 1",
        description = "description 1",
        price = 150.0,
        imageUrl = "",
        isProductSet = false,
        isSpecialBrand = false
    ),
    Product(
        id = 1L,
        name = "product 2",
        description = "description 2",
        price = 150.0,
        imageUrl = "",
        isProductSet = false,
        isSpecialBrand = false
    ),
    Product(
        id = 3L,
        name = "product 3",
        "description 3",
        price = 150.0,
        imageUrl = "",
        isProductSet = false,
        isSpecialBrand = false
    )
)

var fakeReviews = arrayListOf(
    ProductReviews(
        id = 0L,
        reviews = arrayListOf(
            Review(name = "Michel Pakontan", text = "Horrible !", rating = 0.0),
            Review(name = "Jane Doe", text = "Greatfull !", rating = 4.5),
            Review(name = null, text = "ok.", rating = 3.0)
        ),
    )
)