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
    openDraggableBox: () -> Unit,
) {
    CatalogContent {
        catalogItem(label = "ScoreBoard") {
            openScoreBoard()
        }
        catalogItem(label = "Draggable Box") {
            openDraggableBox()
        }
        catalogItem(label = "label 3") {
            // TODO
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
