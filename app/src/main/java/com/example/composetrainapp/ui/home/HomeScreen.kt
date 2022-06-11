package com.example.composetrainapp.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composetrainapp.domain.model.response.Character
import com.example.composetrainapp.ui.HomeViewModel
import com.example.composetrainapp.ui.NavigationRoutes
import com.example.composetrainapp.ui.utils.*

@Composable
fun HomeScreen(
    screen: NavigationRoutes,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state: UiState<List<Character>> by viewModel.uiState.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    Box(modifier = Modifier.fillMaxSize()) {
        if (screen == NavigationRoutes.ColumnRow) {
            ColumnRowView(
                scope = scope,
                scaffoldState = scaffoldState,
                listState = listState
            )
        } else if (screen == NavigationRoutes.Grid) {
            GridView(
                scope = scope,
                scaffoldState = scaffoldState,
            )
        }
    }
}
