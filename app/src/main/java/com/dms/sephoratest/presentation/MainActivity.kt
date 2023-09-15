package com.dms.sephoratest.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dms.sephoratest.R
import com.dms.sephoratest.presentation.main.MainScreen
import com.dms.sephoratest.presentation.main.MainViewModel
import com.dms.sephoratest.presentation.productdetail.ProductDetailScreen
import com.dms.sephoratest.presentation.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {
        private const val productIdExtra = "PRODUCT_ID_EXTRA"
    }

    private val mainViewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Scaffold(
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.smallTopAppBarColors(
                            containerColor = Color.Black,
                            titleContentColor = Color.White,
                        ),
                        title = {
                            Text(stringResource(id = R.string.app_name))
                        }
                    )
                },
            ) { innerPadding ->
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.MainScreen.route
                    ) {
                        composable(route = Screen.MainScreen.route) {
                            MainScreen(
                                mainViewModel = mainViewModel,
                                onProductClick = { navController.navigate(route = Screen.ProductDetailScreen.route + "/$it") }
                            )
                        }
                        composable(
                            route = Screen.ProductDetailScreen.route + "/{$productIdExtra}",
                            arguments = listOf(
                                navArgument(productIdExtra) {
                                    type = NavType.LongType
                                }
                            )
                        ) {
                            val productId =
                                it.arguments?.getLong(productIdExtra) ?: 0 // Cannot be null
                            ProductDetailScreen(
                                mainViewModel = mainViewModel,
                                productId = productId
                            )
                        }
                    }
                }
            }
        }
    }
}


