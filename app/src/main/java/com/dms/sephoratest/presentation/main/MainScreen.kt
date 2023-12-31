package com.dms.sephoratest.presentation.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dms.sephoratest.R
import com.dms.sephoratest.presentation.MainViewModel
import com.dms.sephoratest.presentation.ProductUiModel
import com.dms.sephoratest.presentation.ProductsListMock

@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    onProductClick: (Long) -> Unit
) {
    val viewState by mainViewModel.viewState.collectAsState()

    LaunchedEffect(key1 = true, block = {
        mainViewModel.showBackButton(show = false)
    })

    BackHandler(true) {
        // Disable navigate back
    }

    MainContent(
        isLoading = viewState.isLoading,
        isRefreshed = viewState.isRefreshed,
        hasError = viewState.hasError,
        productsList = viewState.productsList,
        refreshList = mainViewModel::refreshProductsList,
        onQueryChanged = mainViewModel::filterByName,
        onProductClick = onProductClick
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun MainContent(
    isLoading: Boolean,
    isRefreshed: Boolean,
    hasError: Boolean,
    productsList: List<ProductUiModel>,
    refreshList: () -> Unit,
    onQueryChanged: (String) -> Unit,
    onProductClick: (Long) -> Unit
) {
    val pullRefreshState = rememberPullRefreshState(isRefreshed, refreshList)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 8.dp)
            .pullRefresh(pullRefreshState)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = stringResource(R.string.hello),
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                modifier = Modifier.padding(top = 2.dp),
                text = stringResource(R.string.welcome_to_sephora),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            SearchBar(
                modifier = Modifier.padding(top = 8.dp),
                onQueryChanged = onQueryChanged
            )

            if (!isLoading) {
                if (!hasError) {
                    if (productsList.isEmpty()) {
                        EmptyList(modifier = Modifier
                            .fillMaxWidth()
                            .weight(weight = 1f))
                    } else {
                        ProductsList(
                            modifier = Modifier
                                .padding(top = 8.dp),
                            productsList = productsList,
                            onProductClick = onProductClick
                        )

                        Spacer(modifier = Modifier.weight(weight = 1f))
                    }
                } else {
                    ErrorComponent(modifier = Modifier
                        .fillMaxWidth()
                        .weight(weight = 1f))
                }
            } else {
                Spacer(modifier = Modifier.weight(weight = 1f))
            }

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(R.string.copyright),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall
            )
        }

        PullRefreshIndicator(
            modifier = Modifier.align(Alignment.TopCenter),
            refreshing = isRefreshed,
            state = pullRefreshState
        )
    }

    if (isLoading) {
        Loader(
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Composable
private fun EmptyList(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(size = 60.dp),
            imageVector = Icons.Filled.SearchOff,
            contentDescription = null,
            tint = Color.Gray
        )

        Text(
            modifier = Modifier.padding(top = 6.dp),
            text = stringResource(R.string.no_result),
            color = Color.Gray,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
private fun ErrorComponent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(size = 60.dp),
            imageVector = Icons.Filled.ErrorOutline,
            contentDescription = null,
            tint = Color.Gray
        )

        Text(
            modifier = Modifier.padding(top = 6.dp),
            text = stringResource(R.string.an_error_happen),
            color = Color.Gray,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
private fun Loader(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(color = Color.Transparent.copy(alpha = 0.1f))
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(alignment = Alignment.Center),
            color = Color.White
        )
    }
}

@Preview
@Composable
private fun MainContentPreview() {
    MainContent(
        isLoading = false,
        isRefreshed = false,
        hasError = false,
        productsList = ProductsListMock,
        refreshList = { },
        onQueryChanged = { },
        onProductClick = { }
    )
}