package com.example.composetrainapp.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.composetrainapp.R
import com.example.composetrainapp.domain.model.Character
import com.example.composetrainapp.ui.RickMortyViewModel
import com.example.composetrainapp.ui.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun NavGraphBuilder.addDetail(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    changeScreen: () -> Unit,
) {
    composable(
        route = "${Routes.DetailCharacter.route}/{id}",
        arguments = listOf(
            navArgument("id") {
                type = NavType.IntType
                nullable = false
            }
        )
    ) { backStackEntry ->
        changeScreen()
        DetailScreen(
            backStackEntry.arguments?.getInt("id") ?: 0,
            scaffoldState,
            scope
        )
    }
}

@Composable
fun DetailScreen(
    characterId: Int,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    viewModel: RickMortyViewModel = hiltViewModel(),
) {
    val state: UiState<Character> by viewModel.characterState.collectAsStateWithLifecycle()
    val color by viewModel.backgroundColor
    var isClicked by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(Unit) { viewModel.getSpecificCharacter(characterId) }

    if (state.error != null) {
        LaunchedEffect(Unit) {
            scope.launch {
                showSnackBarWithArg(
                    scaffoldState,
                    "Error",
                    "retry",
                    characterId,
                    viewModel::getSpecificCharacter
                )
            }
        }
    }

    state.StateView(
        loadingView = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize()
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
    ) { character ->
        val scrollState = rememberScrollState(0)
        Box(modifier = Modifier.fillMaxSize()) {
            Spacer(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                color,
                                MaterialTheme.colors.background
                            )
                        )
                    )
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp, start = 80.dp, end = 80.dp, bottom = 12.dp)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(character.image)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(id = R.drawable.place_holder)
                    )
                    ToggleButton(
                        isClicked = isClicked,
                        backgroundColor = Color.LightGray.copy(alpha = 0.3f),
                        iconColor = Color(0xFFE13760),
                        clickedIconVector = Icons.Default.Favorite,
                        notClickedIconVector = Icons.Default.FavoriteBorder,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(start = 12.dp)
                    ) {
                        isClicked = it
                        viewModel.onClickEvent(it, character)
                        if (it) {
                            context.showToast(context.getString(R.string.save_favorite_character))
                        } else {
                            context.showToast(context.getString(R.string.delete_favorite_character))
                        }
                    }
                }
                CharacterProfileSection("Name", character.name)
                Spacer(modifier = Modifier.height(12.dp))
                CharacterProfileSection("Gender", character.gender)
                Spacer(modifier = Modifier.height(12.dp))
                CharacterProfileSection("Speices", character.species)
            }
        }
    }
}

@Composable
fun CharacterProfileSection(
    title: String,
    content: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(text = title, style = MaterialTheme.typography.subtitle1)
        }
        Divider(modifier = Modifier.padding(horizontal = 12.dp))
        Text(text = content, style = MaterialTheme.typography.body2)
    }
}