package com.dms.sephoratest.presentation.util

sealed class Screen(val route: String) {
    object SplashScreen: Screen("splash_screen")
    object MainScreen: Screen("main_screen")
    object ProductDetailScreen: Screen("product_detail_screen")
}