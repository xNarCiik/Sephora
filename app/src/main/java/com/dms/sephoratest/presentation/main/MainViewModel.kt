package com.dms.sephoratest.presentation.main

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

    private var _loading = MutableStateFlow(value = false)
    private var _productsList = MutableStateFlow<List<ProductUiModel>>(value = arrayListOf())

    private var productsListFull = listOf<ProductUiModel>()

    @Suppress("UNCHECKED_CAST")
    val viewState = combine(
        _loading,
        _productsList
    ) { params ->
        val loading = params[0] as Boolean
        val productsList = params[1] as List<ProductUiModel>

        MainUiModel(
            loading = loading,
            productsList = productsList
        )
    }.stateIn(viewModelScope, SharingStarted.Lazily, MainUiModel())

    init {
        viewModelScope.launch {
            // Transform ProductsDto list to ProductUiModel list
            _loading.value = true
            productsListFull = productsApi.getProductsList().map { it.toDomain() }.map {
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
            _loading.value = false
            updateProductsList(productsList = productsListFull)
        }
    }

    fun filterByName(name: String) {
        updateProductsList(productsList = productsListFull.filter { it.name.contains(name) })
    }

    private fun updateProductsList(productsList: List<ProductUiModel>) {
        _productsList.value = productsList
    }
}