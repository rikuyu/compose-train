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
    openThreadsCard: () -> Unit,
    openCanvas: () -> Unit,
    openSpotLight: () -> Unit,
) {
    CatalogContent {
        catalogItem("ScoreBoard", openScoreBoard)
        catalogItem("Draggable Box", openDraggableBox)
        catalogItem("Threads Card", openThreadsCard)
        catalogItem("Canvas", openCanvas)
        catalogItem("Spot Light", openSpotLight)
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
