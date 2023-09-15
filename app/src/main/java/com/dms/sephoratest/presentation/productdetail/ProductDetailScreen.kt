package com.dms.sephoratest.presentation.productdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dms.sephoratest.R
import com.dms.sephoratest.presentation.main.MainViewModel
import com.dms.sephoratest.presentation.main.ProductUiModel
import com.dms.sephoratest.presentation.main.ProductsListMock

@Composable
fun ProductDetailScreen(
    mainViewModel: MainViewModel,
    productId: Long
) {
    val viewState by mainViewModel.viewState.collectAsState()
    val product = viewState.productsList.find { it.id == productId }

    if (product != null) {
        ProductDetailContent(product = product)
    } else {
        Text("Error loading")
    }
}

@Composable
private fun ProductDetailContent(
    product: ProductUiModel
) {
    ProductItem(modifier = Modifier.padding(all = 8.dp), product = product)
}

@Composable
private fun ProductItem(
    modifier: Modifier = Modifier, product: ProductUiModel
) {
    Card(modifier = modifier) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
                model = product.imageUrl,
                placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                error = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "product image",
            )

            Text(
                modifier = Modifier.padding(vertical = 4.dp),
                text = product.name,
                fontWeight = FontWeight.Bold
            )

            Text(
                modifier = Modifier.padding(bottom = 4.dp),
                text = product.price.toString() + "€",
                fontWeight = FontWeight.Bold,
                color = Color.Red
            )
        }
    }
}

@Preview
@Composable
private fun ProductDetailContentPreview() {
    ProductDetailContent(product = ProductsListMock.first())
}