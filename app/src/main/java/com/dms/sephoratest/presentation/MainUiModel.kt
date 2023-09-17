package com.dms.sephoratest.presentation

data class MainUiModel(
    val showTopBar: Boolean = false,
    val isLoading: Boolean = false,
    val isRefreshed: Boolean = false,
    val hasError: Boolean = false,
    val productsList: List<ProductUiModel> = arrayListOf(),
    val sortBestToWorst: Boolean = true
)

data class ProductUiModel(
    val id: Long = 0,
    val name: String = "",
    val description: String,
    val price: Double,
    val imageUrl: String,
    val isProductSet: Boolean,
    val isSpecialBrand: Boolean,
    var reviews: List<ReviewUiModel> = arrayListOf()
)

data class ReviewUiModel(
    val name: String?,
    val text: String?,
    val rating: Double?
)

val ProductsListMock = arrayListOf(
    ProductUiModel(
        name = "Size Up - Mascara Volume Extra Large Immédiat",
        description = "Craquez pour le Mascara Size Up de Sephora Collection : Volume XXL immédiat, cils ultra allongés et recourbés ★ Formule vegan longue tenue ✓",
        price = 140.00,
        imageUrl = "https://dev.sephora.fr/on/demandware.static/-/Library-Sites-SephoraV2/default/dw521a3f33/brands/institbanner/SEPHO_900_280_institutional_banner_20210927_V2.jpg",
        isProductSet = true,
        isSpecialBrand = true,
        reviews = arrayListOf()
    ),
    ProductUiModel(
        name = "Size Up - Mascara Volume Extra Large Immédiat",
        description = "Craquez pour le Mascara Size Up de Sephora Collection : Volume XXL immédiat, cils ultra allongés et recourbés ★ Formule vegan longue tenue ✓",
        price = 140.00,
        imageUrl = "https://dev.sephora.fr/on/demandware.static/-/Library-Sites-SephoraV2/default/dw521a3f33/brands/institbanner/SEPHO_900_280_institutional_banner_20210927_V2.jpg",
        isProductSet = true,
        isSpecialBrand = true,
        reviews = arrayListOf()
    ),
    ProductUiModel(
        name = "Size Up - Mascara Volume Extra Large Immédiat",
        description = "Craquez pour le Mascara Size Up de Sephora Collection : Volume XXL immédiat, cils ultra allongés et recourbés ★ Formule vegan longue tenue ✓",
        price = 140.00,
        imageUrl = "https://dev.sephora.fr/on/demandware.static/-/Library-Sites-SephoraV2/default/dw521a3f33/brands/institbanner/SEPHO_900_280_institutional_banner_20210927_V2.jpg",
        isProductSet = true,
        isSpecialBrand = true,
        reviews = arrayListOf()
    ),
    ProductUiModel(
        name = "Size Up - Mascara Volume Extra Large Immédiat",
        description = "Craquez pour le Mascara Size Up de Sephora Collection : Volume XXL immédiat, cils ultra allongés et recourbés ★ Formule vegan longue tenue ✓",
        price = 140.00,
        imageUrl = "https://dev.sephora.fr/on/demandware.static/-/Library-Sites-SephoraV2/default/dw521a3f33/brands/institbanner/SEPHO_900_280_institutional_banner_20210927_V2.jpg",
        isProductSet = true,
        isSpecialBrand = true,
        reviews = arrayListOf()
    )
)