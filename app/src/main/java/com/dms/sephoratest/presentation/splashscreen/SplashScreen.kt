package com.dms.sephoratest.presentation.splashscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dms.sephoratest.R
import kotlinx.coroutines.delay

private const val TIME_BEFORE_NAVIGATE = 600L

@Composable
fun SplashScreen(
    navigateToMainScreen: () -> Unit
) {
    LaunchedEffect(key1 = true, block = {
        delay(timeMillis = TIME_BEFORE_NAVIGATE)
        navigateToMainScreen()
    })

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ) {
        Image(
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .size(size = 160.dp),
            painter = painterResource(id = R.mipmap.ic_launcher_foreground),
            contentDescription = ""
        )
    }
}