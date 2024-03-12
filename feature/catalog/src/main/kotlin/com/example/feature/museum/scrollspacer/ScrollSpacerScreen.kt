package com.example.feature.museum.scrollspacer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

private val headerHeightDp = 56.dp

@Composable
internal fun ScrollSpacerScreen() {
    val lazyState = rememberLazyListState()
    val density = LocalDensity.current
    val headerHeightPx = with(density) { headerHeightDp.toPx() }
    val headerPosition = lazyState.headerPosition(headerHeightPx.toInt())

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            state = lazyState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = headerHeightDp),
        ) {
            items(30) {
                Item(
                    "$it",
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .height(if (it % 2 == 0) 120.dp else 80.dp),
                )
            }
        }
        Row(
            Modifier
                .offset { IntOffset(0, -headerPosition) }
                .height(headerHeightDp)
                .fillMaxWidth()
                .background(Color.Blue.copy(alpha = 0.2f)),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = "HEADER")
        }
    }
}

@Composable
fun LazyListState.headerPosition(headerHeightPx: Int): Int {
    var previousIndex = remember(this) { firstVisibleItemIndex }
    var previousScrollOffset = remember(this) { firstVisibleItemScrollOffset }
    var previousItemHeight = remember(this) { heightAtOrZero(firstVisibleItemIndex) }
    var scrollAmount = remember(this) { 0 }
    return remember(this) {
        derivedStateOf {
            scrollAmount += if (previousIndex == firstVisibleItemIndex) {
                firstVisibleItemScrollOffset - previousScrollOffset
            } else {
                if (previousIndex > firstVisibleItemIndex) {
                    val currentScroll = heightAtOrZero(firstVisibleItemIndex) - firstVisibleItemScrollOffset
                    -previousScrollOffset - currentScroll
                } else {
                    val lastScroll = previousItemHeight - previousScrollOffset
                    lastScroll + firstVisibleItemScrollOffset
                }
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
                previousItemHeight = heightAtOrZero(firstVisibleItemIndex)
            }
            scrollAmount = scrollAmount.coerceIn(0, headerHeightPx)
            scrollAmount
        }
    }.value
}


fun LazyListState.heightAtOrZero(index: Int): Int {
    return layoutInfo.visibleItemsInfo.firstOrNull { it.index == index }?.size ?: 0
}

@Composable
private fun Item(
    label: String,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(12.dp),
                color = Color.Gray,
            ),
    ) {
        Text(text = "item: $label")
    }
}
