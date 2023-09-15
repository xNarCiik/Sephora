package com.dms.sephoratest.data.model.mapper

import com.dms.sephoratest.data.model.ProductDto
import com.dms.sephoratest.domain.model.Product

internal fun ProductDto.toDomain(): Product {
    return Product(
        id = product_id,
        name = product_name,
        description = description,
        price = price,
        imageUrl = images_url.small,
        isProductSet = is_productSet,
        isSpecialBrand = is_special_brand
    )
}