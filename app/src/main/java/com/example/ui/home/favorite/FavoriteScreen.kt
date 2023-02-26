package com.example.ui.home.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.composetrainapp.R
import com.example.ui.home.RickMortyViewModel
import com.example.ui.utils.*
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
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    navController: NavHostController,
    viewModel: RickMortyViewModel = hiltViewModel(),
) {
    val uiState by viewModel.favoriteCharacters.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    val context = LocalContext.current

    LaunchedEffect(Unit) { scope.launch { viewModel.getFavoriteCharacterList() } }

    if (uiState.error != null) {
        LaunchedEffect(Unit) {
            scope.launch { showSnackBar(scaffoldState, "error") }
        }
    }

    if (uiState.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize()
        ) {
            CircularProgressIndicator()
        }
    } else if (uiState.error != null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize()
        ) {
            TextButton(onClick = {
                viewModel.getFavoriteCharacterList()
            }) {
                Text(text = "Retry")
            }
        }
    } else {
        if (uiState.characters.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize()
            ) {
                Text(text = LocalContext.current.getString(R.string.no_favorite_items))
            }
        } else {
            Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn(state = listState, modifier = Modifier.padding(4.dp)) {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = LocalContext.current.getString(
                                    R.string.num_favorite_items,
                                    uiState.characters.size
                                ),
                                style = MaterialTheme.typography.subtitle1
                            )
                        }
                    }
                    item { Divider() }
                    items(uiState.characters) { character ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(86.dp)
                                .clickable {
                                    navController.navigate(
                                        Routes.DetailCharacter.createRoute(
                                            character.id
                                        )
                                    )
                                }
                                .padding(4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(character.image)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = null,
                                contentScale = ContentScale.FillHeight,
                                placeholder = painterResource(id = R.drawable.place_holder)
                            )
                            Text(
                                text = character.name,
                                modifier = Modifier
                                    .weight(weight = 1f, fill = true)
                                    .padding(start = 12.dp),
                                style = MaterialTheme.typography.subtitle1,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            ToggleButton(
                                isClicked = true,
                                backgroundColor = Color.LightGray.copy(alpha = 0.3f),
                                iconColor = Color(0xFFFE4E98),
                                clickedIconVector = Icons.Default.Favorite,
                                notClickedIconVector = Icons.Default.FavoriteBorder,
                            ) {
                                viewModel.onClickHeartIcon(it, character, true)
                                context.showToast(context.getString(R.string.delete_favorite_character))
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Icon(Icons.Filled.ArrowForward, contentDescription = null, tint = Color.Gray)
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                        Divider()
                    }
                }
            }
        }
    }
}