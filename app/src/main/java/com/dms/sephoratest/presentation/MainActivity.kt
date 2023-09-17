package com.dms.sephoratest.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dms.sephoratest.R
import com.dms.sephoratest.presentation.main.MainScreen
import com.dms.sephoratest.presentation.productdetail.ProductDetailScreen
import com.dms.sephoratest.presentation.splashscreen.SplashScreen
import com.dms.sephoratest.presentation.util.Screen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {
        private const val PRODUCT_ID_EXTRA = "PRODUCT_ID_EXTRA"
    }

    private val mainViewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val snackbarHostState = remember { SnackbarHostState() }
            val scope = rememberCoroutineScope()
            val navController = rememberNavController()

            val viewState by mainViewModel.viewState.collectAsState()

            LaunchedEffect(key1 = viewState.hasError, block = {
                if (viewState.hasError) {
                    scope.launch {
                        val result = snackbarHostState.showSnackbar(
                            message = getString(R.string.snackback_error_get_products),
                            actionLabel = getString(R.string.retry),
                            duration = SnackbarDuration.Long
                        )

                        if (result == SnackbarResult.ActionPerformed) {
                            mainViewModel.refreshProductsList()
                        }
                    }
                }
            })

            Scaffold(
                snackbarHost = { SnackbarHost(snackbarHostState) },
                topBar = {
                    if (viewState.showTopBar) {
                        Column {
                            CenterAlignedTopAppBar(
                                colors = TopAppBarDefaults.largeTopAppBarColors(
                                    containerColor = Color.White,
                                    titleContentColor = Color.Black,
                                ),
                                title = {
                                    Text(
                                        text = stringResource(id = R.string.app_name),
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                },
                                navigationIcon = {
                                    if (viewState.showBackButton) {
                                        IconButton(onClick = { navController.popBackStack() }) {
                                            Icon(
                                                modifier = Modifier
                                                    .size(size = 28.dp),
                                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                                contentDescription = ""
                                            )
                                        }
                                    }
                                }
                            )

                            Divider()
                        }
                    }
                },
            ) { innerPadding ->
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Screen.SplashScreen.route
                    ) {
                        composable(route = Screen.SplashScreen.route) {
                            SplashScreen(
                                navigateToMainScreen = {
                                    navController.navigate(route = Screen.MainScreen.route)
                                    mainViewModel.showTopBar()
                                }
                            )
                        }
                        composable(route = Screen.MainScreen.route) {
                            MainScreen(
                                mainViewModel = mainViewModel,
                                onProductClick = { navController.navigate(route = Screen.ProductDetailScreen.route + "/$it") }
                            )
                        }
                        composable(
                            route = Screen.ProductDetailScreen.route + "/{$PRODUCT_ID_EXTRA}",
                            arguments = listOf(
                                navArgument(PRODUCT_ID_EXTRA) {
                                    type = NavType.LongType
                                }
                            )
                        ) {
                            val productId =
                                it.arguments?.getLong(PRODUCT_ID_EXTRA) ?: 0 // Cannot be null
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


