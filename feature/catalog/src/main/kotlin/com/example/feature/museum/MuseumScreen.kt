package com.example.feature.museum

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MuseumScreen(
    openScoreBoard: () -> Unit,
    openDraggableBox: () -> Unit,
    openThreadsCard: () -> Unit,
    openCanvas: () -> Unit,
    openSpotLight: () -> Unit,
) {
    MuseumContent {
        museumItem("ScoreBoard", openScoreBoard)
        museumItem("Draggable Box", openDraggableBox)
        museumItem("Threads Card", openThreadsCard)
        museumItem("Canvas", openCanvas)
        museumItem("Spot Light", openSpotLight)
    }
}

@Composable
fun MuseumContent(content: LazyListScope.() -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        content()
    }
}