package com.example.ui.home.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.composetrainapp.R
import com.example.model.Character
import com.example.ui.home.RickMortyViewModel
import com.example.ui.utils.Routes
import com.example.ui.utils.collectAsStateWithLifecycle
import com.example.ui.utils.compose.FullScreenErrorView
import com.example.ui.utils.compose.FullScreenLoadingIndicator
import com.example.ui.utils.compose.ToggleButton
import com.example.ui.utils.compose.TrainAppImage
import com.example.ui.utils.showSnackBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun NavGraphBuilder.addFavorite(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    navController: NavHostController,
) {
    composable(route = Routes.Favorite.route) {
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

    LaunchedEffect(Unit) { scope.launch { viewModel.getFavoriteCharacters() } }

    if (uiState.error != null) {
        LaunchedEffect(Unit) {
            scope.launch { showSnackBar(scaffoldState, "error") }
        }
    }

    if (uiState.isLoading) {
        FullScreenLoadingIndicator()
    } else if (uiState.error != null) {
        FullScreenErrorView { viewModel.getFavoriteCharacters() }
    } else {
        if (uiState.characters.isEmpty()) {
            EmptyView()
        } else {
            Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn(state = listState, modifier = Modifier.padding(4.dp)) {
                    item { FavoriteCountItem(uiState.characters.size) }
                    item { Divider() }
                    items(uiState.characters) {
                        FavoriteItem(
                            character = it,
                            onClickFavorite = viewModel::onClickFavorite,
                            navController = navController
                        )
                        Divider()
                    }
                }
            }
        }
    }
}

@Composable
fun FavoriteItem(
    character: Character,
    onClickFavorite: (Boolean, Character, Boolean) -> Unit,
    navController: NavHostController,
) {
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
        TrainAppImage(url = character.image)
        Text(
            text = character.name,
            modifier = Modifier
                .weight(weight = 1f)
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
            onClickFavorite(it, character, true)
        }
        Spacer(modifier = Modifier.width(12.dp))
        Icon(Icons.Filled.ArrowForward, contentDescription = null, tint = Color.Gray)
        Spacer(modifier = Modifier.width(8.dp))
    }
}

@Composable
fun FavoriteCountItem(favoriteSize: Int) {
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
                favoriteSize
            ),
            style = MaterialTheme.typography.subtitle1
        )
    }
}

@Composable
fun EmptyView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = LocalContext.current.getString(R.string.no_favorite_items))
    }
}