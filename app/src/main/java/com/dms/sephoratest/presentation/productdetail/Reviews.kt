package com.dms.sephoratest.presentation.productdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dms.sephoratest.R
import com.dms.sephoratest.presentation.ProductUiModel
import com.dms.sephoratest.presentation.ProductsListMock
import com.dms.sephoratest.presentation.ReviewUiModel
import com.dms.sephoratest.presentation.core.RatingBar
import java.text.DecimalFormat

@Composable
fun Reviews(
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
                textDecoration = TextDecoration.Underline
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
                    .padding(all = 6.dp)
            ) {
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
private fun ReviewsPreview() {
    Reviews(product = ProductsListMock.first(), sortBestToWorst = true, onSortRatingPressed = { })
}