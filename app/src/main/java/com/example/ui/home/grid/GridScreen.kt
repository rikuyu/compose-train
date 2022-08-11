package com.example.ui.home.grid

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.composetrainapp.R
import com.example.domain.model.Character
import com.example.ui.home.RickMortyViewModel
import com.example.ui.utils.Routes
import com.example.ui.utils.UiState
import com.example.ui.utils.collectAsStateWithLifecycle
import com.example.ui.utils.showSnackBar
import com.example.ui.utils.theme.Purple200
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun NavGraphBuilder.addGrid(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    navController: NavHostController,
    changeScreen: () -> Unit,
) {
    composable(route = Routes.Grid.route) {
        changeScreen()
        GridScreen(
            scope = scope,
            scaffoldState = scaffoldState,
            navController = navController
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GridScreen(
    modifier: Modifier = Modifier,
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    navController: NavHostController,
    viewModel: RickMortyViewModel = hiltViewModel(),
) {
    val state: UiState<List<Character>> by viewModel.characterListState.collectAsStateWithLifecycle()
    val listState = rememberLazyGridState()
    val isShowButton by remember { derivedStateOf { listState.firstVisibleItemIndex != 0 } }
    var gridNum by remember { mutableStateOf(2) }

    LaunchedEffect(Unit) {
        scope.launch { viewModel.getCharacters() }
    }

    if (state.error != null) {
        LaunchedEffect(Unit) {
            scope.launch {
                showSnackBar(scaffoldState, "Error", "retry", viewModel::getCharacters)
            }
        }
    }

    Box {
        state.StateView(
            loadingView = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center),
                ) {
                    CircularProgressIndicator()
                }
            },
            errorView = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center),
                ) {
                    TextButton(onClick = {
                        scope.launch { viewModel.getCharacters() }
                    }) {
                        Text(text = "Retry")
                    }
                }
            }
        ) { characterList ->
            Column(verticalArrangement = Arrangement.Top) {
                LazyHorizontalGrid(
                    rows = GridCells.Fixed(gridNum),
                    modifier = modifier.height(150.dp),
                    contentPadding = PaddingValues(
                        start = 8.dp,
                        end = 4.dp,
                        top = 12.dp,
                        bottom = 0.dp
                    )
                ) {
                    items(characterList, key = { it.id }) {
                        HorizontalCharacterItem(character = it)
                    }
                }
                Spacer(modifier = modifier.height(10.dp))
                SwipeRefresh(
                    state = rememberSwipeRefreshState(state.isRefreshing),
                    onRefresh = { viewModel.refreshCharacters() },
                ) {
                    LazyVerticalGrid(
                        state = listState,
                        columns = GridCells.Fixed(gridNum),
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
                            items = characterList,
                            key = { _, c -> c.id },
                        ) { _, c ->
                            VerticalCharacterItem(
                                character = c,
                                onClick = {
                                    navController.navigate(
                                        Routes.DetailCharacter.createRoute(
                                            c.id
                                        )
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
        Column(modifier = Modifier.align(alignment = Alignment.BottomEnd).padding(12.dp)) {
            AnimatedVisibility(visible = isShowButton) {
                IconButton(
                    onClick = { scope.launch { listState.animateScrollToItem(0) } },
                    modifier = Modifier
                        .padding(10.dp)
                        .background(color = Purple200, shape = CircleShape)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_upward),
                        contentDescription = null, tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            IconButton(
                onClick = {
                    gridNum = if (gridNum == 2) {
                        3
                    } else {
                        2
                    }
                },
                modifier = Modifier
                    .padding(10.dp)
                    .background(color = Purple200, shape = CircleShape)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_refresh),
                    contentDescription = null, tint = Color.White
                )
            }
        }
    }
}

@Composable
fun HorizontalCharacterItem(
    character: Character,
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
                    onTap = { },
                )
            }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(character.image)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier.padding(end = 4.dp),
            contentScale = ContentScale.FillHeight,
            placeholder = painterResource(id = R.drawable.place_holder)
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
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(210.dp)
            .clickable { onClick() },
        verticalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(character.image)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
            placeholder = painterResource(id = R.drawable.place_holder)
        )
        Text(
            text = character.name,
            style = MaterialTheme.typography.subtitle1
        )
    }
}