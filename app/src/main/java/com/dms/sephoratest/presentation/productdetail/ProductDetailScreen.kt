package com.dms.sephoratest.presentation.productdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
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
fun ProductDetailScreen(
    mainViewModel: MainViewModel,
    productId: Long
) {
    val viewState by mainViewModel.viewState.collectAsState()
    val product = viewState.productsList.find { it.id == productId }

    LaunchedEffect(key1 = true, block = {
        mainViewModel.showBackButton(show = true)
    })

    if (product != null) {
        ProductDetailContent(
            productId = productId,
            product = product,
            sortBestToWorst = viewState.sortBestToWorst,
            onSortRatingPressed = mainViewModel::sortReviewsByBestToWorst,
            onAddToCart = mainViewModel::addToCart
        )
    } else {
        Text(text = "Error loading") // Cant happen
    }
}

@Composable
private fun ProductDetailContent(
    productId: Long,
    product: ProductUiModel,
    sortBestToWorst: Boolean,
    onSortRatingPressed: (Boolean) -> Unit,
    onAddToCart: (Long) -> Unit
) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .padding(bottom = 8.dp)
    ) {
        ProductInfo(
            product = product
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .weight(weight = 1f)
        ) {
            Reviews(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                product = product,
                sortBestToWorst = sortBestToWorst,
                onSortRatingPressed = onSortRatingPressed
            )
        }

        Divider(modifier = Modifier.fillMaxWidth())

        AddToCartBottom(
            modifier = Modifier
                .padding(top = 4.dp)
                .padding(horizontal = 8.dp),
            price = product.price,
            onAddToCart = { onAddToCart(productId) }
        )
    }
}

@Composable
fun AddToCartBottom(
    modifier: Modifier = Modifier,
    price: Double,
    onAddToCart: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = price.toString() + stringResource(R.string.euro_device),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp),
            shape = RectangleShape,
            onClick = onAddToCart,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            Text(text = stringResource(R.string.add_to_cart))
        }
    }
}

@Preview
@Composable
private fun ProductDetailContentPreview() {
    ProductDetailContent(
        productId = 0L,
        product = ProductsListMock.first(),
        sortBestToWorst = true,
        onSortRatingPressed = { },
        onAddToCart = { }
    )
}