package com.dms.sephoratest.presentation.productdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dms.sephoratest.R
import com.dms.sephoratest.presentation.ProductUiModel
import com.dms.sephoratest.presentation.ProductsListMock

@Composable
fun ProductInfo(
    modifier: Modifier = Modifier,
    product: ProductUiModel
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
            model = product.imageUrl,
            placeholder = painterResource(id = R.drawable.ic_load),
            error = painterResource(id = R.drawable.ic_error),
            contentDescription = "product image",
        )

        Text(
            modifier = Modifier
                .padding(top = 8.dp)
                .padding(horizontal = 8.dp),
            text = product.name,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )

        Text(
            modifier = Modifier
                .padding(all = 8.dp),
            text = product.description,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun ProductInfoPreview() {
    ProductInfo(product = ProductsListMock.first())
}