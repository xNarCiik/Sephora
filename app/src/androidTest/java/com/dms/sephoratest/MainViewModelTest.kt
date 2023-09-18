package com.dms.sephoratest

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dms.sephoratest.domain.model.Product
import com.dms.sephoratest.domain.usecase.GetProductReviewsUseCase
import com.dms.sephoratest.domain.usecase.GetProductsListUseCase
import com.dms.sephoratest.presentation.MainViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
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
        Dispatchers.setMain(UnconfinedTestDispatcher())

        getProductsListUseCase = mockk()
        getProductReviewsUseCase = mockk()

        viewModel = MainViewModel(
            getProductsListUseCase = getProductsListUseCase,
            getProductReviewsUseCase = getProductReviewsUseCase,
            dispatcher = UnconfinedTestDispatcher()
        )
    }

    @Test
    fun showTopBarTest() = runTest {
        val job = launch {
            viewModel.viewState.collect { }
        }
        // By default, we don't show the top bar
        assertEquals(false, viewModel.viewState.value.showTopBar)

        viewModel.showTopBar()
        advanceUntilIdle()

        // Check if top bar is showed
        assertEquals(true, viewModel.viewState.value.showTopBar)
        job.cancel()
    }

    @Test
    fun showBackButtonTest() = runTest {
        val job = launch {
            viewModel.viewState.collect { }
        }
        // By default, we don't show the back bar
        assert(!viewModel.viewState.value.showBackButton)

        viewModel.showBackButton(show = true)
        advanceUntilIdle()
        assert(viewModel.viewState.value.showBackButton)

        // Try revert back
        viewModel.showBackButton(show = false)
        advanceUntilIdle()
        assert(!viewModel.viewState.value.showBackButton)

        job.cancel()
    }

    @Test
    fun isLoadingTest() = runTest {
        coEvery { getProductsListUseCase.run() } returns fakeProducts
        coEvery { getProductReviewsUseCase.run() } returns arrayListOf()

        val job = launch {
            viewModel.viewState.collect {}
        }

        // By default, we don't show loader
        assert(!viewModel.viewState.value.isLoading)

        // TODO find way to test loading = true
        advanceUntilIdle()

        // After load (success or not) we don't show the loader
        assert(!viewModel.viewState.value.isLoading)

        job.cancel()
    }

    @Test
    fun loadListProductTest() = runTest {
        coEvery { getProductsListUseCase.run() } returns fakeProducts
        coEvery { getProductReviewsUseCase.run() } returns arrayListOf()

        val job = launch {
            viewModel.viewState.collect { }
        }
        // By default, we don't have any products loaded
        assert(viewModel.viewState.value.productsList.isEmpty())

        advanceUntilIdle()

        assertEquals(3, viewModel.viewState.value.productsList.count())

        job.cancel()
    }

    @Test
    fun addToCartListTest() = runTest {
        coEvery { getProductsListUseCase.run() } returns fakeProducts

        val job = launch {
            viewModel.viewState.collect { }
        }
        // By default, we have empty cartList
        assert(viewModel.viewState.value.cartList.isEmpty())
        advanceUntilIdle()

        viewModel.addToCart(productId = 0)
        advanceUntilIdle()

        // Assert product id 0 is added
        assertEquals(1, viewModel.viewState.value.cartList.count())
        assertEquals(0, viewModel.viewState.value.cartList.first().id)

        viewModel.addToCart(productId = 1)
        advanceUntilIdle()

        // Assert product id 1 is added
        assertEquals(2, viewModel.viewState.value.cartList.count())
        assertEquals(1, viewModel.viewState.value.cartList.last().id)

        // Try to add a wrong product id
        viewModel.addToCart(productId = -1)
        advanceUntilIdle()

        // Assert we don't add a wrong product
        assertEquals(2, viewModel.viewState.value.cartList.count())

        job.cancel()
    }

    @Test
    fun errorWhenLoadProductsTest() = runTest {
        coEvery { getProductsListUseCase.run() }.throws(Exception())

        val job = launch {
            viewModel.viewState.collect { }
        }
        // By default, we don't have any products loaded
        assert(!viewModel.viewState.value.hasError)

        advanceUntilIdle()

        assert(viewModel.viewState.value.hasError)

        job.cancel()
    }

    @Test
    fun sortBestToWorstTest() = runTest {
        val job = launch {
            viewModel.viewState.collect { }
        }
        // By default, we sort review to best to worst
        assert(viewModel.viewState.value.sortBestToWorst)

        // Test update value to false
        viewModel.sortReviewsByBestToWorst(sortBestToWorst = false)
        advanceUntilIdle()
        assert(!viewModel.viewState.value.sortBestToWorst)

        // Test revert back to true
        viewModel.sortReviewsByBestToWorst(sortBestToWorst = true)
        advanceUntilIdle()
        assert(viewModel.viewState.value.sortBestToWorst)

        job.cancel()
    }
}