package com.example.composetrainapp.ui.home

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.composetrainapp.domain.model.Character
import com.example.composetrainapp.ui.RickMortyViewModel
import com.example.composetrainapp.ui.utils.Routes
import com.example.composetrainapp.ui.utils.UiState
import com.example.composetrainapp.ui.utils.collectAsStateWithLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun NavGraphBuilder.addFavorite(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    navController: NavHostController,
    changeScreen: () -> Unit,
) {
    composable(route = Routes.Favorite.route) {
        changeScreen()
        FavoriteScreen(
            scope = scope,
            scaffoldState = scaffoldState,
            navController = navController
        )
    }
}

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    navController: NavHostController,
    viewModel: RickMortyViewModel = hiltViewModel(),
) {
    val state: UiState<List<Character>> by viewModel.favoriteCharacterState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) { scope.launch { viewModel.getFavoriteCharacters() } }
}