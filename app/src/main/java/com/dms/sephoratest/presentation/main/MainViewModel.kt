package com.dms.sephoratest.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
) : ViewModel() {

    private var _loading = MutableStateFlow(value = false)
    private var _productsList = MutableStateFlow<List<String>>(value = arrayListOf())

    private var productsListFull = arrayListOf<String>()

    @Suppress("UNCHECKED_CAST")
    val viewState = combine(
        _loading,
        _productsList
    ) { params ->
        val loading = params[0] as Boolean
        val productsList = params[1] as List<String>

        MainUiModel(
            loading = loading,
            productsList = productsList
        )
    }.stateIn(viewModelScope, SharingStarted.Lazily, MainUiModel())

    init {
        productsListFull = arrayListOf("Test", "Test2", "Test3", "Test4", "Test5")
        _productsList.value = productsListFull
    }

    fun filterByName(name: String) {
        _productsList.value = productsListFull.filter { it.contains(name) }
    }
}