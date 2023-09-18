package com.dms.sephoratest

import androidx.test.ext.junit.runners.AndroidJUnit4
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
    
    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        getProductsListUseCase = mockk()
        getProductReviewsUseCase = mockk()

        coEvery { getProductsListUseCase.run() } returns fakeProducts
        coEvery { getProductReviewsUseCase.run() } returns fakeReviews

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
    fun loadListProductReviewTest() = runTest {
        val job = launch {
            viewModel.viewState.collect { }
        }
        // By default, we don't have any reviews loaded
        assert(viewModel.viewState.value.productsList.isEmpty())

        advanceUntilIdle()

        // Test if we have reviews in first product
        assert(viewModel.viewState.value.productsList.first().reviews.isNotEmpty())

        job.cancel()
    }

    @Test
    fun addToCartListTest() = runTest {
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
        advanceUntilIdle() // Waiting first load result

        val job = launch {
            viewModel.viewState.collect { }
        }

        // By default, we don't have any products loaded
        assert(!viewModel.viewState.value.hasError)

        coEvery { getProductsListUseCase.run() }.throws(Exception())
        viewModel.refreshProductsList() // Refresh list with an exception

        advanceUntilIdle()

        assert(viewModel.viewState.value.hasError)

        job.cancel()
    }

    @Test
    fun errorWhenLoadProductsReviewsTest() = runTest {
        advanceUntilIdle() // Waiting first load result

        val job = launch {
            viewModel.viewState.collect { }
        }

        // By default, we don't have any products loaded
        assert(!viewModel.viewState.value.hasError)

        coEvery { getProductReviewsUseCase.run() }.throws(Exception())
        viewModel.refreshProductsList() // Refresh list with an exception

        advanceUntilIdle()

        assert(viewModel.viewState.value.hasError)

        job.cancel()
    }

    @Test
    fun sortBestToWorstTest() = runTest {
        val job = launch {
            viewModel.viewState.collect { }
        }
        advanceUntilIdle()
        // By default, we sort review to best to worst
        assert(viewModel.viewState.value.sortBestToWorst)

        // Test if list is correctly sorted
        assert(viewModel.viewState.value.productsList.first().reviews.first().name == "Jane Doe")
        assert(viewModel.viewState.value.productsList.first().reviews[1].name == null)
        assert(viewModel.viewState.value.productsList.first().reviews[2].name == "Michel Pakontan")

        // Test update value to false
        viewModel.sortReviewsByBestToWorst(sortBestToWorst = false)
        advanceUntilIdle()
        assert(!viewModel.viewState.value.sortBestToWorst)

        // Test if list is correctly sorted
        assert(viewModel.viewState.value.productsList.first().reviews.first().name == "Michel Pakontan")
        assert(viewModel.viewState.value.productsList.first().reviews[1].name == null)
        assert(viewModel.viewState.value.productsList.first().reviews[2].name == "Jane Doe")

        // Test revert back to true
        viewModel.sortReviewsByBestToWorst(sortBestToWorst = true)
        advanceUntilIdle()
        assert(viewModel.viewState.value.sortBestToWorst)

        job.cancel()
    }

    @Test
    fun computeRatingSumReviewsTest() = runTest {
        val job = launch {
            viewModel.viewState.collect { }
        }

        advanceUntilIdle()
        assertEquals(2.5, viewModel.viewState.value.productsList.first().rating)

        job.cancel()
    }
}