package com.example.feature.museum.snapshotFlow

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
internal fun SnapshotFlowScreen1(
    viewModel: SnapshotFlowViewModel,
) {
    val count by viewModel.count.collectAsState()
    val countInstance by viewModel.countInstance.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SnapshotFlowBody1(
            count = count,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.Magenta.copy(alpha = 0.1f)),
        )
        HorizontalDivider()
        SnapshotFlowScreen2(
            count = count,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.Blue.copy(alpha = 0.1f)),
        )
    }
}

data class Count(val count: Int)

@Composable
internal fun SnapshotFlowBody1(
    count: Count,
    modifier: Modifier = Modifier,
) {
    var isActive by remember { mutableStateOf(false) }

    LaunchedEffect(count) {
        Log.d("SnapshotFlow", "1: LaunchedEffect")
        snapshotFlow { count.count }
            .collect { Log.d("SnapshotFlow", "1: $it") }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "isActive: $isActive, count: ${count.count}",
            fontSize = 16.sp,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { isActive = !isActive }) {
            Text(text = "Tap")
        }
    }
}

@Composable
internal fun SnapshotFlowScreen2(
    count: Count,
    modifier: Modifier = Modifier,
) {
    var isActive by remember { mutableStateOf(false) }

    LaunchedEffect(count.count) {
        Log.d("SnapshotFlow", "2: LaunchedEffect")
        snapshotFlow { count.count }
            .collect { Log.d("SnapshotFlow", "2: $it") }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "isActive: $isActive, count: ${count.count}",
            fontSize = 16.sp,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { isActive = !isActive }) {
            Text(text = "Tap")
        }
    }
}

// https://developer.android.com/jetpack/compose/side-effects#snapshotFlow
@Composable
fun SnapshotFlowScreen2() {
    val listState = rememberLazyListState()
    var isActive by remember { mutableStateOf(false) }

    LaunchedEffect(listState) {
        Log.d("SnapshotFlow", "listState: LaunchedEffect")
        snapshotFlow { listState.firstVisibleItemIndex }
            .distinctUntilChanged()
            .collect {
                Log.d("SnapshotFlow", "index: $it")
            }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 72.dp)
                .align(Alignment.TopCenter),
        ) {
            items(30) {
                Column {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                    ) {
                        Text(
                            text = "Item $it",
                            fontSize = 16.sp,
                        )
                    }
                    HorizontalDivider()
                }
            }
        }
        Button(
            onClick = { isActive = !isActive },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .height(52.dp)
                .padding(bottom = 8.dp),
        ) {
            Text(text = "Tap isActive: $isActive")
        }
    }
}

