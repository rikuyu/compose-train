package com.example.feature.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CatalogScreen(
    openScoreBoard: () -> Unit,
) {
    CatalogContent {
        catalogItem(label = "label 1") {
            openScoreBoard()
        }
        catalogItem(label = "label 2") {

        }
        catalogItem(label = "label 3") {

        }
    }
}

@Composable
fun CatalogContent(content: LazyListScope.() -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        content()
    }
}
