package com.dms.sephoratest

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dms.sephoratest.domain.model.Product
import com.dms.sephoratest.domain.usecase.GetProductReviewsUseCase
import com.dms.sephoratest.domain.usecase.GetProductsListUseCase
import com.dms.sephoratest.presentation.MainViewModel
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel

    private lateinit var getProductsListUseCase: GetProductsListUseCase
    private lateinit var getProductReviewsUseCase: GetProductReviewsUseCase

    private var fakeProducts = arrayListOf(
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

    @Before
    fun setUp() {
        getProductsListUseCase = mockk()
        getProductReviewsUseCase = mockk()

        viewModel = MainViewModel(
            getProductsListUseCase = getProductsListUseCase,
            getProductReviewsUseCase = getProductReviewsUseCase,
            dispatcher = UnconfinedTestDispatcher()
        )
    }
}