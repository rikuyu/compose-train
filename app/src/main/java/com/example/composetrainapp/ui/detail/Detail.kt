package com.example.composetrainapp.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.composetrainapp.R
import com.example.composetrainapp.domain.model.response.Character
import com.example.composetrainapp.ui.HomeViewModel
import com.example.composetrainapp.ui.home.AttributeIcons
import com.example.composetrainapp.ui.utils.UiState
import com.example.composetrainapp.ui.utils.collectAsStateWithLifecycle
import com.example.composetrainapp.ui.utils.showSnackBarWithArg
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun Detail(
    characterId: Int,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state: UiState<Character> by viewModel.characterState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getSpecificCharacter(characterId)
    }

    state.StateView(loadingView = {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize()
        ) {
            CircularProgressIndicator()
        }
    }, errorView = {
            scope.launch {
                showSnackBarWithArg(
                    scaffoldState,
                    "Error",
                    "retry",
                    characterId,
                    viewModel::getSpecificCharacter
                )
            }
        }) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(it.image)
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
                AttributeIcons(it)
                Text(
                    it.name,
                    style = MaterialTheme.typography.subtitle1
                )
            }
        }
    }
}
