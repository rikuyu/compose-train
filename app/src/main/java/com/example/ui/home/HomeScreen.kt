package com.example.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.domain.model.Character
import com.example.ui.RickMortyViewModel
import com.example.ui.home.column_row.ColumnRowScreen
import com.example.ui.home.grid.GridScreen
import com.example.ui.utils.Routes
import com.example.ui.utils.UiState
import com.example.ui.utils.collectAsStateWithLifecycle

@Composable
fun HomeScreen(
    screen: Routes,
    navController: NavHostController,
    viewModel: RickMortyViewModel = hiltViewModel(),
) {
    val state: UiState<List<Character>> by viewModel.characterListState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    Box(modifier = Modifier.fillMaxSize()) {
        if (screen == Routes.ColumnRow) {
            ColumnRowScreen(
                scope = scope,
                scaffoldState = scaffoldState,
                navController = navController
            )
        } else if (screen == Routes.Grid) {
            GridScreen(
                scope = scope,
                scaffoldState = scaffoldState,
                navController = navController
            )
        }
    }
}