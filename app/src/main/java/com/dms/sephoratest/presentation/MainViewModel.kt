package com.dms.sephoratest.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dms.sephoratest.data.api.ProductsApi
import com.dms.sephoratest.data.model.mapper.toDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val productsApi: ProductsApi,
) : ViewModel() {

    private var _showTopBar = MutableStateFlow(value = false)
    private var _isLoading = MutableStateFlow(value = false)
    private val _isRefreshed = MutableStateFlow(value = false)
    private var _hasError = MutableStateFlow(value = false)
    private var _productsList = MutableStateFlow<List<ProductUiModel>>(value = arrayListOf())
    private var _sortBestToWorst = MutableStateFlow(value = true)

    private var productsListFull = listOf<ProductUiModel>()

    @Suppress("UNCHECKED_CAST")
    val viewState = combine(
        _showTopBar,
        _isLoading,
        _isRefreshed,
        _hasError,
        _productsList,
        _sortBestToWorst,
    ) { params ->
        val showTopBar = params[0] as Boolean
        val isLoading = params[1] as Boolean
        val isRefreshed = params[2] as Boolean
        val hasError = params[3] as Boolean
        val productsList = params[4] as List<ProductUiModel>
        val sortBestToWorst = params[5] as Boolean

        MainUiModel(
            showTopBar = showTopBar,
            isLoading = isLoading,
            isRefreshed = isRefreshed,
            hasError = hasError,
            productsList = productsList,
            sortBestToWorst = sortBestToWorst
        )
    }.stateIn(viewModelScope, SharingStarted.Lazily, MainUiModel())

    init {
        loadProductsList()
    }

    fun showTopBar() {
        _showTopBar.value = true
    }

    fun refreshProductsList() {
        loadProductsList(isRefreshed = true)
    }

    fun filterByName(name: String) {
        updateProductsList(productsList = productsListFull.filter { it.name.contains(name) })
    }

    fun sortReviewsByBestToWorst(sortBestToWorst: Boolean) {
        _sortBestToWorst.value = sortBestToWorst
        productsListFull.map { productUiModel ->
            productUiModel.reviews =
                if (sortBestToWorst) productUiModel.reviews.sortedByDescending { it.rating }
                else productUiModel.reviews.sortedBy { it.rating }
        }
        updateProductsList(productsList = productsListFull)
    }

    private fun loadProductsList(isRefreshed: Boolean = false) {
        viewModelScope.launch {
            _isLoading.value = true
            _hasError.value = false
            _isRefreshed.value = isRefreshed
            try {
                val responseProductsList = productsApi.getProductsList()
                if (responseProductsList.isSuccessful) {
                    responseProductsList.body()?.let { productsList ->
                        // Transform ProductsDto list to ProductUiModel list
                        productsListFull = productsList.map { it.toDomain() }.map {
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

                        val responseProductReviews = productsApi.getProductReviews()
                        if (responseProductReviews.isSuccessful) {
                            _hasError.value = false
                            responseProductReviews.body()?.let { productReviews ->
                                productReviews.map { it.toDomain() }.forEach { productReview ->
                                    productsListFull.find { it.id == productReview.id }?.reviews =
                                        productReview.reviews.map {
                                            ReviewUiModel(
                                                name = it.name,
                                                text = it.text,
                                                rating = it.rating
                                            )
                                        }
                                }
                            }
                        } else {
                            _hasError.value = true
                        }

                        sortReviewsByBestToWorst(sortBestToWorst = _sortBestToWorst.value)
                    }
                } else {
                    _hasError.value = true
                    updateProductsList(productsList = arrayListOf())
                }
                loadingFinished()
            } catch (exception: Exception) {
                _hasError.value = true
                updateProductsList(productsList = arrayListOf())
                loadingFinished()
            }
        }
    }

    private fun loadingFinished() {
        _isRefreshed.value = false
        _isLoading.value = false
    }

    private fun updateProductsList(productsList: List<ProductUiModel>) {
        _productsList.value = productsList
    }
}