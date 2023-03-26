package com.example.ui.home.grid

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.model.Character
import com.example.ui.home.CharactersUiState
import com.example.ui.home.RickMortyViewModel
import com.example.ui.utils.Routes
import com.example.ui.utils.collectAsStateWithLifecycle
import com.example.ui.utils.compose.FullScreenErrorView
import com.example.ui.utils.compose.FullScreenLoadingIndicator
import com.example.ui.utils.compose.TrainAppImage
import com.example.ui.utils.showSnackBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun NavGraphBuilder.addGrid(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    navController: NavHostController,
) {
    composable(route = Routes.Grid.route) {
        GridScreen(
            scope = scope,
            scaffoldState = scaffoldState,
            navController = navController
        )
    }
}

@Composable
fun GridScreen(
    modifier: Modifier = Modifier,
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    navController: NavHostController,
    viewModel: RickMortyViewModel = hiltViewModel(),
) {
    val uiState: CharactersUiState by viewModel.characters.collectAsStateWithLifecycle()
    val listState = rememberLazyGridState()

    LaunchedEffect(Unit) {
        scope.launch { viewModel.getCharacters() }
    }

    if (uiState.error != null) {
        LaunchedEffect(Unit) {
            scope.launch {
                showSnackBar(scaffoldState, "Error", "retry", viewModel::getCharacters)
            }
        }
    }

    val state = rememberPullRefreshState(
        refreshing = uiState.isRefreshing,
        onRefresh = { viewModel.refreshCharacters() }
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
                        bottom = 0.dp
                    )
                ) {
                    items(uiState.characters.take(6), key = { it.id }) {
                        HorizontalCharacterItem(
                            character = it,
                            onClickItem = {
                                navController.navigate(
                                    Routes.DetailCharacter.createRoute(
                                        it.id
                                    )
                                )
                            }
                        )
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
                            bottom = 24.dp
                        ),
                        modifier = modifier,
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        itemsIndexed(
                            items = uiState.characters.takeLast(14),
                            key = { _, c -> c.id },
                        ) { _, c ->
                            VerticalCharacterItem(
                                character = c,
                                onClickItem = {
                                    navController.navigate(
                                        Routes.DetailCharacter.createRoute(
                                            c.id
                                        )
                                    )
                                }
                            )
                        }
                    }
                    PullRefreshIndicator(
                        refreshing = uiState.isRefreshing,
                        state = state,
                        modifier = Modifier.align(Alignment.TopCenter)
                    )
                }
            }
        }
    }
}

@Composable
fun HorizontalCharacterItem(
    character: Character,
    onClickItem: () -> Unit,
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
                    onTap = { onClickItem() },
                )
            }
    ) {
        TrainAppImage(
            modifier = Modifier.padding(end = 4.dp),
            url = character.image
        )
        Text(
            text = character.name,
            style = MaterialTheme.typography.subtitle1
        )
    }
}

@Composable
fun VerticalCharacterItem(
    character: Character,
    onClickItem: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(210.dp)
            .clickable { onClickItem() },
        verticalArrangement = Arrangement.Center
    ) {
        TrainAppImage(
            modifier = Modifier.fillMaxWidth(),
            url = character.image,
            contentScale = ContentScale.FillWidth
        )
        Text(
            text = character.name,
            style = MaterialTheme.typography.subtitle1
        )
    }
}