package com.dms.sephoratest.presentation.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dms.sephoratest.R
import com.dms.sephoratest.presentation.ProductUiModel
import com.dms.sephoratest.presentation.ProductsListMock
import com.dms.sephoratest.presentation.core.RatingBar

@Composable
fun ProductsList(
    modifier: Modifier = Modifier,
    productsList: List<ProductUiModel>,
    onProductClick: (Long) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(count = 2)
    ) {
        items(count = productsList.size) { index ->
            val product = productsList[index]
            ProductItem(
                modifier = Modifier
                    .padding(all = 8.dp)
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
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                modifier = Modifier.height(height = 80.dp),
                contentScale = ContentScale.FillHeight,
                model = product.imageUrl,
                placeholder = painterResource(id = R.drawable.ic_load),
                error = painterResource(id = R.drawable.ic_error),
                contentDescription = "product image",
            )

            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = product.name,
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center,
                maxLines = 1
            )

            Spacer(modifier = Modifier.fillMaxHeight())

            val rating = product.rating
            Row(
                modifier = Modifier.padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (rating != null) {
                    RatingBar(rating = rating)
                } else {
                    RatingBar(rating = 5.0, starsColor = Color.Gray)
                }

                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = stringResource(R.string.nb_review).format(product.reviews.size),
                    style = MaterialTheme.typography.labelSmall
                )
            }

            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = "${product.price}" + stringResource(id = R.string.euro_device),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold
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