package com.dms.sephoratest.presentation.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dms.sephoratest.R
import com.dms.sephoratest.presentation.main.productslist.ProductsList

@Composable
fun MainScreen(
    mainViewModel: MainViewModel = hiltViewModel(),
    onProductClick: () -> Unit
) {
    val viewState by mainViewModel.viewState.collectAsState()

    MainContent(
        productsList = viewState.productsList,
        onQueryChanged = mainViewModel::filterByName,
        onProductClick = onProductClick
    )
}

@Composable
private fun MainContent(
    productsList: List<ProductUiModel>,
    onQueryChanged: (String) -> Unit,
    onProductClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 8.dp)
    ) {
        SearchBar(
            onQueryChanged = onQueryChanged
        )

        ProductsList(
            modifier = Modifier.padding(top = 8.dp),
            productsList = productsList,
            onProductClick = onProductClick
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchBar(
    onQueryChanged: (String) -> Unit
) {
    var text by remember { mutableStateOf(value = "") }

    TextField(
        value = text,
        onValueChange = {
            text = it
            onQueryChanged(it)
        },
        label = { Text(text = stringResource(R.string.search)) },
        leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = null) },
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview
@Composable
private fun MainContentPreview() {
    MainContent(
        productsList = ProductsListMock,
        onQueryChanged = { },
        onProductClick = { }
    )
}