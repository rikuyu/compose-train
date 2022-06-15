package com.example.composetrainapp.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.composetrainapp.domain.model.response.Character
import com.example.composetrainapp.ui.HomeViewModel
import com.example.composetrainapp.ui.utils.*
import com.example.composetrainapp.ui.utils.NavigationRoutes

@Composable
fun HomeScreen(
    screen: NavigationRoutes,
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state: UiState<List<Character>> by viewModel.characterListState.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    Box(modifier = Modifier.fillMaxSize()) {
        if (screen == NavigationRoutes.ColumnRow) {
            ColumnRowView(
                scope = scope,
                scaffoldState = scaffoldState,
                listState = listState,
                navController = navController
            )
        } else if (screen == NavigationRoutes.Grid) {
            GridView(
                scope = scope,
                scaffoldState = scaffoldState,
                navController = navController
            )
        }
    }
}
