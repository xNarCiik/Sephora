package com.dms.sephoratest.presentation.main.productslist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.dms.sephoratest.presentation.ProductUiModel
import com.dms.sephoratest.presentation.ProductsListMock

@Composable
fun ProductsList(
    modifier: Modifier = Modifier,
    productsList: List<ProductUiModel>,
    onProductClick: (Long) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(count = productsList.size) { index ->
            val product = productsList[index]
            ProductItem(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .clickable { onProductClick(product.id) },
                product = product
            )
        }
    }
}

@Composable
private fun ProductItem(
    modifier: Modifier = Modifier,
    product: ProductUiModel
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
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold)

            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = product.price.toString() + "€",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Red
            )
        }
    }
}

@Preview
@Composable
private fun ProductsListPreview() {
    ProductsList(
        productsList = ProductsListMock,
        onProductClick = { }
    )
}