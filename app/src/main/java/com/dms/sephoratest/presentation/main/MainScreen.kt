package com.dms.sephoratest.presentation.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dms.sephoratest.R
import com.dms.sephoratest.presentation.main.productslist.ProductsList

@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    onProductClick: (Long) -> Unit
) {
    val viewState by mainViewModel.viewState.collectAsState()

    MainContent(
        isLoading = viewState.isLoading,
        isRefreshed = viewState.isRefreshed,
        hasError = viewState.hasError,
        productsList = viewState.productsList,
        refreshList = mainViewModel::refreshList,
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
            SearchBar(
                onQueryChanged = onQueryChanged
            )

            if (!hasError) {
                if (productsList.isEmpty()) {
                    // TODO UI FOR PRODUCTSLISTEMPTY
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "C'est vide !")
                    }
                } else {
                    ProductsList(
                        modifier = Modifier
                            .padding(top = 8.dp),
                        productsList = productsList,
                        onProductClick = onProductClick
                    )
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Désolé, il semblerait qu'il y ai eu un problème lors de la récupération des données.")
                }
            }
        }

        PullRefreshIndicator(
            modifier = Modifier.align(Alignment.TopCenter),
            refreshing = isRefreshed,
            state = pullRefreshState
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchBar(
    modifier: Modifier = Modifier,
    onQueryChanged: (String) -> Unit
) {
    var text by remember { mutableStateOf(value = "") }

    TextField(
        modifier = modifier.fillMaxWidth(),
        value = text,
        onValueChange = {
            text = it
            onQueryChanged(it)
        },
        label = { Text(text = stringResource(R.string.search)) },
        leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = null) }
    )
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