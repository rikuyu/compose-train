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
    openShare: () -> Unit,
    openAutoScroll: () -> Unit,
    openCircularProgressBar: () -> Unit,
    openBankCard: () -> Unit,
    openShakeIcon: () -> Unit,
    openRemember1: () -> Unit,
    openRemember2: () -> Unit,
    openAnimTextChatGPT: () -> Unit,
    openScrollSpacer: () -> Unit,
    openPictureInPicture: () -> Unit,
    openCoroutineTimer: () -> Unit,
) {
    MuseumContent {
        museumItem(label = "ScoreBoard", onClick = openScoreBoard)
        museumItem(label = "Draggable Box", onClick = openDraggableBox)
        museumItem(label = "Threads Card", onClick = openThreadsCard)
        museumItem(label = "Canvas", onClick = openCanvas)
        museumItem(label = "Spot Light", onClick = openSpotLight)
        museumItem(label = "Share", onClick = openShare)
        museumItem(label = "Auto Scroll", onClick = openAutoScroll)
        museumItem(label = "Circular Progress Bar", onClick = openCircularProgressBar)
        museumItem(label = "BankCard", onClick = openBankCard)
        museumItem(label = "Animation Icons", onClick = openShakeIcon)
        museumItem(
            label1 = "Remember Random",
            onClick1 = openRemember1,
            label2 = "Remember Data Class",
            onClick2 = openRemember2,
        )
        museumItem(label = "AnimText like ChatGPT", onClick = openAnimTextChatGPT)
        museumItem(label = "Scroll Spacer", onClick = openScrollSpacer)
        museumItem(label = "Picture In Picture", onClick = openPictureInPicture)
        museumItem(label = "Coroutine Timer", onClick = openCoroutineTimer)
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
