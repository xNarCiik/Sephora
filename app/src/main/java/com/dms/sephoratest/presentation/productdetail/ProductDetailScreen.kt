package com.dms.sephoratest.presentation.productdetail

import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.dms.sephoratest.presentation.MainViewModel
import com.dms.sephoratest.presentation.ProductUiModel
import com.dms.sephoratest.presentation.ProductsListMock
import com.dms.sephoratest.presentation.ReviewUiModel
import java.text.DecimalFormat

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
            product = product,
            sortBestToWorst = viewState.sortBestToWorst,
            onSortRatingPressed = mainViewModel::sortReviewsByBestToWorst
        )
    } else {
        Text(text = "Error loading") // Cant happen
    }
}

@Composable
private fun ProductDetailContent(
    product: ProductUiModel,
    sortBestToWorst: Boolean,
    onSortRatingPressed: (Boolean) -> Unit
) {
    Column(modifier = Modifier.padding(bottom = 8.dp)) {
        ProductCard(
            product = product
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .weight(weight = 1f)
        ) {
            ReviewsCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                product = product,
                sortBestToWorst = sortBestToWorst,
                onSortRatingPressed = onSortRatingPressed
            )
        }

        Divider(modifier = Modifier.fillMaxWidth())

        Row(
            modifier = Modifier
                .padding(top = 4.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = product.price.toString() + stringResource(R.string.euro_device),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
                onClick = { /* NOT IMPLEMENTED */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text(text = stringResource(R.string.buy))
            }
        }
    }
}

@Composable
private fun ProductCard(
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
            placeholder = painterResource(id = R.drawable.ic_launcher_foreground), // TODO change placeholder
            error = painterResource(id = R.drawable.ic_launcher_foreground),
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

@Composable
private fun ReviewsCard(
    modifier: Modifier = Modifier,
    product: ProductUiModel,
    sortBestToWorst: Boolean,
    onSortRatingPressed: (Boolean) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = stringResource(R.string.reviews).format(product.reviews.size),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.padding(top = 12.dp))

        if (product.reviews.isEmpty()) {
            Text(
                text = stringResource(R.string.empty_reviews),
                style = MaterialTheme.typography.bodyMedium
            )
        } else {
            ResumeReview(rating = product.rating ?: 0.0)

            Text(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .clickable { onSortRatingPressed(!sortBestToWorst) },
                text = stringResource(R.string.sort_by).format(
                    stringResource(
                        if (sortBestToWorst) R.string.positif_review else R.string.negatif_review
                    )
                ),
            )

            ReviewsList(
                modifier = Modifier.padding(top = 12.dp),
                reviews = product.reviews
            )
        }

        Spacer(modifier = Modifier.padding(vertical = 8.dp))
    }
}

@Composable
private fun ResumeReview(
    modifier: Modifier = Modifier,
    rating: Double
) {
    Column(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            RatingBar(rating = rating, iconSize = 25.dp)

            val ratingFormated = DecimalFormat("#.##").format(rating)
            Text(text = "($ratingFormated/5)")
        }
    }
}

@Composable
private fun ReviewsList(
    modifier: Modifier = Modifier, reviews: List<ReviewUiModel>
) {
    LazyColumn(modifier = modifier) {
        items(count = reviews.size) { index ->
            val review = reviews[index]
            Column(modifier = Modifier.padding(all = 6.dp)) {
                review.rating?.let {
                    RatingBar(rating = it)
                }

                review.name?.let {
                    Text(
                        modifier = Modifier.padding(top = 6.dp),
                        text = it,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
                review.text?.let {
                    Text(
                        modifier = Modifier.padding(top = 6.dp),
                        text = it,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                if (index + 1 != reviews.size) {
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

@Preview
@Composable
private fun ProductDetailContentPreview() {
    ProductDetailContent(
        product = ProductsListMock.first(),
        sortBestToWorst = true,
        onSortRatingPressed = { }
    )
}