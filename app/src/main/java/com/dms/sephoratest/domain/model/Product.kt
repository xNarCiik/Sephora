package com.dms.sephoratest.domain.model

data class Product(
    val id: Long,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val isProductSet: Boolean,
    val isSpecialBrand: Boolean
)
