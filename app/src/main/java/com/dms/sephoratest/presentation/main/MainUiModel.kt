package com.dms.sephoratest.presentation.main

data class MainUiModel(
    val loading: Boolean = false,
    val productsList: List<ProductUiModel> = arrayListOf()
)

data class ProductUiModel(
    val name: String = "",
    val description: String,
    val price: Double,
    val imageUrl: String,
    val isProductSet: Boolean,
    val isSpecialBrand: Boolean
)

val ProductsListMock = arrayListOf(
    ProductUiModel(
        name = "Size Up - Mascara Volume Extra Large Immédiat",
        description = "Craquez pour le Mascara Size Up de Sephora Collection : Volume XXL immédiat, cils ultra allongés et recourbés ★ Formule vegan longue tenue ✓",
        price = 140.00,
        imageUrl = "https://dev.sephora.fr/on/demandware.static/-/Library-Sites-SephoraV2/default/dw521a3f33/brands/institbanner/SEPHO_900_280_institutional_banner_20210927_V2.jpg",
        isProductSet = true,
        isSpecialBrand = true
    ),
    ProductUiModel(
        name = "Size Up - Mascara Volume Extra Large Immédiat",
        description = "Craquez pour le Mascara Size Up de Sephora Collection : Volume XXL immédiat, cils ultra allongés et recourbés ★ Formule vegan longue tenue ✓",
        price = 140.00,
        imageUrl = "https://dev.sephora.fr/on/demandware.static/-/Library-Sites-SephoraV2/default/dw521a3f33/brands/institbanner/SEPHO_900_280_institutional_banner_20210927_V2.jpg",
        isProductSet = true,
        isSpecialBrand = true
    ),
    ProductUiModel(
        name = "Size Up - Mascara Volume Extra Large Immédiat",
        description = "Craquez pour le Mascara Size Up de Sephora Collection : Volume XXL immédiat, cils ultra allongés et recourbés ★ Formule vegan longue tenue ✓",
        price = 140.00,
        imageUrl = "https://dev.sephora.fr/on/demandware.static/-/Library-Sites-SephoraV2/default/dw521a3f33/brands/institbanner/SEPHO_900_280_institutional_banner_20210927_V2.jpg",
        isProductSet = true,
        isSpecialBrand = true
    ),
    ProductUiModel(
        name = "Size Up - Mascara Volume Extra Large Immédiat",
        description = "Craquez pour le Mascara Size Up de Sephora Collection : Volume XXL immédiat, cils ultra allongés et recourbés ★ Formule vegan longue tenue ✓",
        price = 140.00,
        imageUrl = "https://dev.sephora.fr/on/demandware.static/-/Library-Sites-SephoraV2/default/dw521a3f33/brands/institbanner/SEPHO_900_280_institutional_banner_20210927_V2.jpg",
        isProductSet = true,
        isSpecialBrand = true
    )
)