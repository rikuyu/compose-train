package com.example.ui.home.grid

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.model.Character
import com.example.ui.home.CharactersUiState
import com.example.ui.home.RickMortyViewModel
import com.example.ui.utils.Routes
import com.example.ui.utils.compose.FullScreenErrorView
import com.example.ui.utils.compose.FullScreenLoadingIndicator
import com.example.ui.utils.compose.TrainAppImage
import com.example.ui.utils.showSnackBar

fun NavGraphBuilder.addGrid(
    scaffoldState: ScaffoldState,
    navController: NavHostController,
) {
    composable(route = Routes.Grid.route) {
        GridScreen(
            scaffoldState = scaffoldState,
            onClickItem = {
                navController.navigate(
                    Routes.DetailCharacter.createRoute(it.id),
                )
            },
        )
    }
}

@Composable
fun GridScreen(
    scaffoldState: ScaffoldState,
    onClickItem: (Character) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RickMortyViewModel = hiltViewModel(),
) {
    val uiState: CharactersUiState by viewModel.characters.collectAsStateWithLifecycle()
    val listState = rememberLazyGridState()

    LaunchedEffect(Unit) { viewModel.getCharacters() }

    if (uiState.error != null) {
        LaunchedEffect(Unit) {
            showSnackBar(scaffoldState, "Error", "retry", viewModel::getCharacters)
        }
    }

    val state = rememberPullRefreshState(
        refreshing = uiState.isRefreshing,
        onRefresh = { viewModel.refreshCharacters() },
    )

    Box {
        if (uiState.isLoading) {
            FullScreenLoadingIndicator()
        } else if (uiState.error != null) {
            FullScreenErrorView { viewModel.getCharacters() }
        } else {
            Column(verticalArrangement = Arrangement.Top) {
                LazyHorizontalGrid(
                    rows = GridCells.Fixed(2),
                    modifier = modifier.height(150.dp),
                    contentPadding = PaddingValues(
                        start = 8.dp,
                        end = 4.dp,
                        top = 12.dp,
                        bottom = 0.dp,
                    ),
                ) {
                    items(uiState.characters.take(6), key = { it.id }) {
                        HorizontalCharacterItem(character = it, onClickItem = onClickItem)
                    }
                }
                Spacer(modifier = modifier.height(10.dp))
                Box(modifier = Modifier.pullRefresh(state)) {
                    LazyVerticalGrid(
                        state = listState,
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(
                            start = 16.dp,
                            end = 16.dp,
                            top = 12.dp,
                            bottom = 24.dp,
                        ),
                        modifier = modifier,
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        itemsIndexed(
                            items = uiState.characters.takeLast(14),
                            key = { _, c -> c.id },
                        ) { _, c ->
                            VerticalCharacterItem(character = c, onClickItem = onClickItem)
                        }
                    }
                    PullRefreshIndicator(
                        refreshing = uiState.isRefreshing,
                        state = state,
                        modifier = Modifier.align(Alignment.TopCenter),
                        backgroundColor = MaterialTheme.colorScheme.background,
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
fun HorizontalCharacterItem(
    character: Character,
    onClickItem: (Character) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .size(220.dp, 60.dp)
            .padding(vertical = 2.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = { /* Called when the gesture starts */ },
                    onDoubleTap = { /* Called on Double Tap */ },
                    onLongPress = { /* Called on Long Press */ },
                    onTap = { onClickItem(character) },
                )
            },
    ) {
        TrainAppImage(
            modifier = Modifier.padding(end = 4.dp),
            url = character.image,
        )
        Text(
            text = character.name,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Composable
fun VerticalCharacterItem(
    character: Character,
    onClickItem: (Character) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClickItem(character) },
        verticalArrangement = Arrangement.Center,
    ) {
        TrainAppImage(
            modifier = Modifier.fillMaxWidth(),
            url = character.image,
            contentScale = ContentScale.FillWidth,
        )
        Text(
            text = character.name,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}