package com.dms.sephoratest.presentation.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dms.sephoratest.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onQueryChanged: (String) -> Unit
) {
    var text by remember { mutableStateOf(value = "") }
    val trailingIcon: @Composable (() -> Unit) = {
        if (text.isNotEmpty()) {
            Icon(
                modifier = Modifier.clickable {
                    text = ""
                    onQueryChanged(text)
                },
                imageVector = Icons.Filled.Close,
                contentDescription = null
            )
        }
    }

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = text,
        onValueChange = {
            text = it
            onQueryChanged(it)
        },
        shape = RoundedCornerShape(size = 16.dp),
        maxLines = 1,
        label = { Text(text = stringResource(R.string.search)) },
        leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = null) },
        trailingIcon = trailingIcon
    )
}

@Preview
@Composable
private fun SearchBarPreview() {
    SearchBar(onQueryChanged = { })
}