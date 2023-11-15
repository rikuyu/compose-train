package com.example.rickandmorty.favorite

import FullScreenErrorView
import FullScreenLoadingIndicator
import Routes
import ToggleButton
import TrainAppImage
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.feature.rickandmorty.R
import com.example.model.Character
import com.example.rickandmorty.RickMortyViewModel
import showSnackBar

@Composable
fun FavoriteScreen(
    scaffoldState: ScaffoldState,
    navController: NavHostController,
    viewModel: RickMortyViewModel = hiltViewModel(),
) {
    val uiState by viewModel.favoriteCharacters.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()

    LaunchedEffect(Unit) { viewModel.getFavoriteCharacters() }

    if (uiState.error != null) {
        LaunchedEffect(Unit) {
            showSnackBar(scaffoldState, "error")
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
                    items(
                        items = uiState.characters,
                        key = { it.id },
                    ) {
                        FavoriteItem(
                            character = it,
                            onClickFavorite = viewModel::onClickFavorite,
                            navController = navController,
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
            .clickable {
                navController.navigate(
                    Routes.DetailCharacter.createRoute(
                        character.id,
                    ),
                )
            }
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        TrainAppImage(url = character.image, modifier = Modifier.size(64.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = character.name,
            modifier = Modifier.weight(weight = 1f),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
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
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = LocalContext.current.getString(
                R.string.num_favorite_items,
                favoriteSize,
            ),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Composable
fun EmptyView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = LocalContext.current.getString(R.string.no_favorite_items),
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}
