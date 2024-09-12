package com.example.feature.museum.remember

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun DataClassScreen(
    viewModel: DataClassViewModel,
) {
    val state by viewModel.uiState.collectAsState()

    SideEffect {
        Log.d("DataClassScreen", "hashCode: ${state.hashCode()}")
    }

    LaunchedEffect(state) {
        Log.d("DataClassScreen", "flag: ${state.flag}, ${state.hashCode()}: key に data class そのもの")
    }

    LaunchedEffect(state.flag) {
        Log.d("DataClassScreen", "flag: ${state.flag}, ${state.hashCode()}: key に flag")
    }

    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(48.dp),
    ) {
        Button(
            modifier = Modifier.width(200.dp),
            onClick = viewModel::setFlagTrue,
        ) {
            Text("Set flag true")
        }
        Button(
            modifier = Modifier.width(200.dp),
            onClick = viewModel::setFlagFalse,
        ) {
            Text("Set flag false")
        }
        Button(
            modifier = Modifier.width(200.dp),
            onClick = viewModel::countUp,
        ) {
            Text("count up")
        }
    }
}
