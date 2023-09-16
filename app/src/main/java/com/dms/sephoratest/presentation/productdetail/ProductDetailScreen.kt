package com.dms.sephoratest.presentation.productdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextAlign
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
    productId: Long,
    onButtonBackPressed: () -> Unit
) {
    val viewState by mainViewModel.viewState.collectAsState()
    val product = viewState.productsList.find { it.id == productId }

    if (product != null) {
        ProductDetailContent(
            product = product,
            onButtonBackPressed = onButtonBackPressed,
        )
    } else {
        Text("Error loading")
    }
}

@Composable
private fun ProductDetailContent(
    product: ProductUiModel,
    onButtonBackPressed: () -> Unit
) {
    Column(modifier = Modifier.padding(all = 4.dp)) {
        Card {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillWidth,
                    model = product.imageUrl,
                    placeholder = painterResource(id = R.drawable.ic_launcher_foreground), // TODO change placeholder
                    error = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "product image",
                )

                Text(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .padding(horizontal = 8.dp),
                    text = product.name,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    modifier = Modifier
                        .padding(top = 6.dp)
                        .padding(horizontal = 8.dp),
                    text = product.description,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )

                Text(
                    modifier = Modifier.padding(all = 8.dp),
                    text = product.price.toString() + "€",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )
            }
        }

        Column(
            modifier = Modifier.weight(weight = 1f)
        ) {
            Card(
                modifier = Modifier
                    .padding(top = 12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = "Reviews",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.padding(top = 6.dp))

                    if (product.reviews.isEmpty()) {
                        Text(
                            text = "Pas de reviews sur ce produit pour le moment.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    } else {
                        LazyColumn {
                            items(count = product.reviews.size) { index ->
                                val review = product.reviews[index]
                                Column(modifier = Modifier.padding(all = 4.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        val name = review.name ?: "Inconnu"
                                        Text(
                                            text = name,
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.Bold
                                        )

                                        Spacer(modifier = Modifier.weight(weight = 1f))

                                        review.rating?.let {
                                            RatingBar(rating = it)
                                        }
                                    }

                                    review.text?.let {
                                        Text(
                                            modifier = Modifier.padding(top = 2.dp),
                                            text = it,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }

                                    if (index + 1 != product.reviews.size) {
                                        Divider(
                                            modifier = Modifier
                                                .padding(top = 12.dp)
                                                .fillMaxWidth()
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.padding(bottom = 4.dp))
                }
            }
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            onClick = onButtonBackPressed,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            Text(text = "Retour")
        }
    }
}

@Preview
@Composable
private fun ProductDetailContentPreview() {
    ProductDetailContent(product = ProductsListMock.first(), onButtonBackPressed = { })
}