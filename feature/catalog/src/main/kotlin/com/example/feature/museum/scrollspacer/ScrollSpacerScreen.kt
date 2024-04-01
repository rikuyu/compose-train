package com.example.feature.museum.scrollspacer

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
internal fun ScrollSpacerScreen() {
    val lazyListState = rememberLazyListState()
    val spacerHeight by rememberSpacerHeight(
        lazyListState = lazyListState,
        divideNumber = 2,
        maxHeight = 120,
        minHeight = 60,
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "ScrollSpacerScreen",
            fontSize = 24.sp,
            modifier = Modifier.padding(16.dp),
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(spacerHeight.dp)
                .background(Color.Blue),
        )
        LazyColumn(
            state = lazyListState,
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(20) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Item $it",
                        fontSize = 16.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
private fun rememberSpacerHeight(
    lazyListState: LazyListState,
    divideNumber: Int,
    maxHeight: Int,
    minHeight: Int,
) = remember {
    derivedStateOf {
        Log.d("IGNINNH", "offset: ${lazyListState.firstVisibleItemScrollOffset}")
        if (lazyListState.firstVisibleItemIndex == 0) {
            (maxHeight - (lazyListState.firstVisibleItemScrollOffset / divideNumber)
                .coerceIn(0, maxHeight - minHeight)).coerceIn(minHeight, maxHeight)
        } else {
            minHeight
        }
    }
}
