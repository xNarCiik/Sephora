package com.dms.sephoratest.presentation.main

data class MainUiModel(
    val loading: Boolean = false,
    val productsList: List<String> = arrayListOf()
)
