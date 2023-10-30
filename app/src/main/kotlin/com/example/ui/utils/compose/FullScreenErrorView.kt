package com.example.ui.utils.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FullScreenErrorView(onClickRetry: (() -> Unit)? = null) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(),
    ) {
        TextButton(
            onClick = { onClickRetry?.invoke() },
        ) {
            Text(text = "Retry", color = MaterialTheme.colorScheme.primary)
        }
    }
}
