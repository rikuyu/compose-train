package com.example.composetrainapp.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.composetrainapp.ui.utils.*
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun BoxScope.ColumnRowView(
    viewModel: HomeViewModel = hiltViewModel(),
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    listState: LazyListState,
    navController: NavHostController,
) {
    val state: UiState<List<Character>> by viewModel.characterListState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .padding(8.dp, 8.dp, 8.dp, 0.dp)
            .align(Alignment.TopCenter)
    ) {
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
                    showSnackBar(scaffoldState, "Error", "retry", viewModel::getCharacters)
                }
            },
            successView = {
                LazyRow {
                    items(it.take(8), key = { it.id }) { character ->
                        Card(
                            elevation = 8.dp,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .padding(6.dp)
                                .clickable {
                                    navController.navigate(
                                        NavigationRoutes.DetailCharacter.createRoute(
                                            character.id
                                        )
                                    )
                                },
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(character.image)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    placeholder = painterResource(id = R.drawable.place_holder)
                                )
                                Column(
                                    Modifier.padding(vertical = 6.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    AttributeIcons(character)
                                    Text(
                                        character.name.substring(0, 10),
                                        style = MaterialTheme.typography.subtitle1
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                SwipeRefresh(
                    state = rememberSwipeRefreshState(state.isRefreshing),
                    onRefresh = { viewModel.refreshCharacters() },
                ) {
                    LazyColumn {
                        items(it, key = { it.id }) { character ->
                            Card(
                                elevation = 4.dp,
                                modifier = Modifier
                                    .padding(2.dp)
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .padding(4.dp)
                                    .clickable {
                                        navController.navigate(
                                            NavigationRoutes.DetailCharacter.createRoute(
                                                character.id
                                            )
                                        )
                                    },
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    AsyncImage(
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data(character.image)
                                            .crossfade(true)
                                            .build(),
                                        contentDescription = null,
                                        contentScale = ContentScale.Fit,
                                        placeholder = painterResource(id = R.drawable.place_holder)
                                    )
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(12.dp),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.Start
                                    ) {
                                        AttributeIcons(character)
                                        Text(
                                            text = character.name,
                                            fontSize = 16.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        )
    }
//    AnimatedVisibility(visible = listState.firstVisibleItemIndex != 0,
//        modifier = Modifier.align(alignment = Alignment.BottomEnd)) {
//        IconButton(onClick = { scope.launch { listState.animateScrollToItem(0) } },
//            modifier = Modifier
//                .padding(16.dp)
//                .background(color = Purple200, shape = CircleShape)) {
//            Icon(painter = painterResource(id = R.drawable.ic_arrow_upward),
//                contentDescription = null, tint = Color.White)
//        }
//    }
}

@Composable
fun AttributeIcons(character: Character) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Icon(
            painter = painterResource(id = getCreatureRes(character.species).first),
            contentDescription = null,
            tint = getCreatureRes(character.species).second
        )
        Icon(
            painter = painterResource(id = getGenderRes(character.gender).first),
            contentDescription = null,
            tint = getGenderRes(character.gender).second
        )
        Icon(
            painter = painterResource(id = getStatusRes(character.status).first),
            contentDescription = null,
            tint = getStatusRes(character.status).second
        )
    }
}

// @Composable
// fun CharactersRowSection(state: UiState<List<Character>>, scope: CoroutineScope, scaffoldState: ScaffoldState) {
//    Column {
//        Text(text = "CharactersRow",
//            color = MaterialTheme.colors.onSurface,
//            fontSize = 22.sp,
//            fontWeight = FontWeight.ExtraBold)
//        Spacer(modifier = Modifier.height(8.dp))
//        when (state) {
//            is HomeUiState.Success -> // @Composable
//            is HomeUiState.Error -> // @Composable
//            is HomeUiState.Loading -> // @Composable
//        }
//    }
// }

// @Composable
// fun CharactersColumnSection(
//    state: UiState<List<Character>>,
//    listState: LazyListState
// ) {
//    Column(modifier = Modifier.fillMaxWidth()) {
//        Text(text = "CharactersColumn",
//            color = MaterialTheme.colors.onSurface,
//            fontSize = 22.sp,
//            fontWeight = FontWeight.ExtraBold)
//        Spacer(modifier = Modifier.height(8.dp))
//        when (state) {
//            is HomeUiState.Success -> @Composable
//            is HomeUiState.Error -> @Composable
//            is HomeUiState.Loading -> @Composable
//        }
//    }
// }
