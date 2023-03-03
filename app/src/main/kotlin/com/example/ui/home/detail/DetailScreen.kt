package com.example.ui.home.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.model.CharacterDetail
import com.example.ui.home.RickMortyViewModel
import com.example.ui.utils.compose.FullScreenLoadingIndicator
import com.example.ui.utils.Routes
import com.example.ui.utils.compose.ToggleButton
import com.example.ui.utils.collectAsStateWithLifecycle
import com.example.ui.utils.compose.TrainAppImage
import com.example.ui.utils.showSnackBarWithArg
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
    val uiState by viewModel.characterDetail.collectAsStateWithLifecycle()
    val backgroundColor by viewModel.backgroundColor.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) { viewModel.getDetail(characterId) }

    if (uiState.isLoading) {
        FullScreenLoadingIndicator()
    } else if (uiState.error != null) {
        LaunchedEffect(Unit) {
            scope.launch {
                showSnackBarWithArg(
                    scaffoldState,
                    requireNotNull(uiState.error).message ?: "error",
                    "retry",
                    characterId,
                    viewModel::getDetail
                )
            }
        }
    } else {
        val data = requireNotNull(uiState.detail)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            backgroundColor,
                            MaterialTheme.colors.background
                        )
                    )
                )
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Box {
                TrainAppImage(
                    url = data.image,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(240.dp)
                        .clip(CircleShape)
                        .align(Alignment.Center)
                )
                ToggleButton(
                    isClicked = data.isFavorite,
                    backgroundColor = Color.LightGray.copy(alpha = 0.5f),
                    iconColor = Color(0xFFFE4E98),
                    clickedIconVector = Icons.Default.Favorite,
                    notClickedIconVector = Icons.Default.FavoriteBorder,
                    modifier = Modifier.align(Alignment.BottomEnd)
                ) {
                    viewModel.onClickFavorite(it, CharacterDetail.convertToCharacter(data))
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            AtrItem("Name", data.name)
            Spacer(modifier = Modifier.height(12.dp))
            AtrItem("Gender", data.gender)
            Spacer(modifier = Modifier.height(12.dp))
            AtrItem("Spices", data.species)
        }
    }
}

@Composable
fun AtrItem(title: String, content: String) {
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
            Text(text = title, style = MaterialTheme.typography.h6)
        }
        Divider()
        Text(text = content, style = MaterialTheme.typography.body1)
    }
}