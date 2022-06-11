package com.example.composetrainapp.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.composetrainapp.R
import com.example.composetrainapp.domain.model.response.Character
import com.example.composetrainapp.ui.HomeViewModel
import com.example.composetrainapp.ui.utils.UiState
import com.example.composetrainapp.ui.utils.collectAsStateWithLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GridView(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
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
                scaffoldState.snackbarHostState.showSnackbar(message = "error")
            }
        })
    { characterList ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 24.dp,
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
                CharacterItemView(character = c)
            }
        }
    }
}

@Composable
fun CharacterItemView(
    character: Character,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable {  },
        verticalArrangement = Arrangement.Center) {
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