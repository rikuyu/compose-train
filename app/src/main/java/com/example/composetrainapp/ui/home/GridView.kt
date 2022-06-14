package com.example.composetrainapp.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.composetrainapp.R
import com.example.composetrainapp.domain.model.response.Character
import com.example.composetrainapp.ui.HomeViewModel
import com.example.composetrainapp.ui.utils.NavigationRoutes
import com.example.composetrainapp.ui.utils.UiState
import com.example.composetrainapp.ui.utils.collectAsStateWithLifecycle
import com.example.composetrainapp.ui.utils.handleSnackBar
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GridView(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    navController: NavHostController,
) {
    val state: UiState<List<Character>> by viewModel.uiState.collectAsStateWithLifecycle()

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
            scope.launch {
                handleSnackBar(scaffoldState, "Error", "retry", viewModel::getCharacters)
            }
        }
    ) { characterList ->
        Column(verticalArrangement = Arrangement.Top) {
            LazyHorizontalGrid(
                rows = GridCells.Fixed(3),
                modifier = modifier.height(150.dp),
                contentPadding = PaddingValues(start = 8.dp, end = 4.dp, top = 12.dp, bottom = 0.dp)
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
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        end = 16.dp,
                        top = 12.dp,
                        bottom = 24.dp
                    ),
                    modifier = modifier,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    itemsIndexed(
                        items = characterList,
                        key = { _, c -> c.id },
                    ) { _, c ->
                        VerticalCharacterItem(
                            character = c,
                            onClick = {
                                navController.navigate(NavigationRoutes.DetailCharacter.createRoute(c.id))
                            }
                        )
                    }
                }
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
            fontSize = 16.sp
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
            fontSize = 16.sp
        )
    }
}
