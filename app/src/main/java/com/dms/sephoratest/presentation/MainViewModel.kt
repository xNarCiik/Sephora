package com.dms.sephoratest.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dms.sephoratest.domain.usecase.GetProductReviewsUseCase
import com.dms.sephoratest.domain.usecase.GetProductsListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getProductsListUseCase: GetProductsListUseCase,
    private val getProductReviewsUseCase: GetProductReviewsUseCase,
    val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private var _showTopBar = MutableStateFlow(value = false)
    private var _showBackButton = MutableStateFlow(value = false)
    private var _isLoading = MutableStateFlow(value = false)
    private val _isRefreshed = MutableStateFlow(value = false)
    private var _hasError = MutableStateFlow(value = false)
    private var _productsList = MutableStateFlow<List<ProductUiModel>>(value = arrayListOf())
    private var _sortBestToWorst = MutableStateFlow(value = true)
    private var _cartList = MutableStateFlow<ArrayList<ProductUiModel>>(value = arrayListOf())

    private var productsListFull = listOf<ProductUiModel>()

    @Suppress("UNCHECKED_CAST")
    val viewState = combine(
        _showTopBar,
        _showBackButton,
        _isLoading,
        _isRefreshed,
        _hasError,
        _productsList,
        _sortBestToWorst,
        _cartList
    ) { params ->
        val showTopBar = params[0] as Boolean
        val showBackButton = params[1] as Boolean
        val isLoading = params[2] as Boolean
        val isRefreshed = params[3] as Boolean
        val hasError = params[4] as Boolean
        val productsList = params[5] as List<ProductUiModel>
        val sortBestToWorst = params[6] as Boolean
        val cartList = params[7] as List<ProductUiModel>

        MainUiModel(
            showTopBar = showTopBar,
            showBackButton = showBackButton,
            isLoading = isLoading,
            isRefreshed = isRefreshed,
            hasError = hasError,
            productsList = productsList,
            sortBestToWorst = sortBestToWorst,
            cartList = cartList
        )
    }.stateIn(viewModelScope, SharingStarted.Lazily, MainUiModel())

    init {
        loadProductsList()
    }

    fun showTopBar() {
        viewModelScope.launch(context = dispatcher) {
            _showTopBar.value = true
        }
    }

    fun showBackButton(show: Boolean) {
        viewModelScope.launch(context = dispatcher) {
            _showBackButton.value = show
        }
    }

    fun refreshProductsList() {
        viewModelScope.launch(context = dispatcher) {
            loadProductsList(isRefreshed = true)
        }
    }

    fun filterByName(name: String) {
        viewModelScope.launch(context = dispatcher) {
            updateProductsList(productsList = productsListFull.filter { it.name.contains(name) })
        }
    }

    fun sortReviewsByBestToWorst(sortBestToWorst: Boolean) {
        viewModelScope.launch(context = dispatcher) {
            _sortBestToWorst.value = sortBestToWorst
            productsListFull.map { productUiModel ->
                productUiModel.reviews =
                    if (sortBestToWorst) productUiModel.reviews.sortedByDescending { it.rating }
                    else productUiModel.reviews.sortedBy { it.rating }
            }
            updateProductsList(productsList = productsListFull)
        }
    }

    fun addToCart(productId: Long) {
        viewModelScope.launch(context = dispatcher) {
            productsListFull.find { it.id == productId }?.let {
                _cartList.value =
                    arrayListOf<ProductUiModel>().apply {
                        addAll(_cartList.value)
                        add(it)
                    }
            }
        }
    }

    private fun loadProductsList(isRefreshed: Boolean = false) {
        viewModelScope.launch(context = dispatcher) {
            _isLoading.value = true
            _hasError.value = false
            _isRefreshed.value = isRefreshed

            try {
                // Transform Products list to ProductUiModel list
                productsListFull = getProductsListUseCase.run().map {
                    ProductUiModel(
                        id = it.id,
                        name = it.name,
                        description = it.description,
                        price = it.price,
                        imageUrl = it.imageUrl,
                        isProductSet = it.isProductSet,
                        isSpecialBrand = it.isSpecialBrand
                    )
                }

                getProductReviewsUseCase.run().forEach { productReview ->
                    productsListFull.find { it.id == productReview.id }?.apply {
                        var sumRating = 0.0
                        reviews =
                            productReview.reviews.map {
                                sumRating += it.rating ?: 0.0
                                ReviewUiModel(
                                    name = it.name,
                                    text = it.text,
                                    rating = it.rating
                                )
                            }
                        rating = sumRating / reviews.size
                    }
                }

                sortReviewsByBestToWorst(sortBestToWorst = _sortBestToWorst.value)
                loadingFinished()
            } catch (exception: Exception) {
                _hasError.value = true
                updateProductsList(productsList = arrayListOf())
                loadingFinished()
            }
        }
    }

    private fun loadingFinished() {
        viewModelScope.launch(context = dispatcher) {
            _isRefreshed.value = false
            _isLoading.value = false
        }
    }

    private fun updateProductsList(productsList: List<ProductUiModel>) {
        viewModelScope.launch(context = dispatcher) {
            _productsList.value = productsList
        }
    }
}