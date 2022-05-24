package com.example.composetrainapp.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.composetrainapp.R
import com.example.composetrainapp.ui.HomeUiState
import com.example.composetrainapp.ui.HomeViewModel
import com.example.composetrainapp.ui.utils.collectAsStateWithLifecycle
import com.example.composetrainapp.ui.utils.theme.Purple200
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.placeholder
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state: HomeUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .padding(8.dp, 8.dp, 8.dp, 0.dp)
            .align(Alignment.TopCenter)) {
            CharactersRowSection(state)
            Spacer(modifier = Modifier.height(12.dp))
            CharactersColumnSection(state, listState)
        }
        AnimatedVisibility(visible = listState.firstVisibleItemIndex != 0,
            modifier = Modifier.align(alignment = Alignment.BottomEnd)) {
            IconButton(onClick = { scope.launch { listState.animateScrollToItem(0) } },
                modifier = Modifier
                    .padding(16.dp)
                    .background(color = Purple200, shape = CircleShape)) {
                Icon(painter = painterResource(id = R.drawable.ic_arrow_upward),
                    contentDescription = null, tint = Color.White)
            }
        }
    }
}

@Composable
fun CharactersRowSection(state: HomeUiState) {
    Column {
        Text(text = "CharactersRow",
            color = MaterialTheme.colors.onSurface,
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold)
        Spacer(modifier = Modifier.height(8.dp))
        when (state) {
            is HomeUiState.Success -> {
                LazyRow {
                    items(state.data.take(8)) { character ->
                        Card(
                            elevation = 8.dp,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .padding(10.dp)
                                .clickable(onClick = {})
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(character.image)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop
                                )
                                Column(Modifier.padding(vertical = 12.dp)) {
                                    Text(
                                        character.name.substring(0, 10),
                                        style = typography.subtitle1
                                    )
                                    // todo
                                }
                            }
                        }
                    }
                }
            }
            is HomeUiState.Error -> LazyRow {
                items(List(8) { 0 }) {
                    Card(
                        elevation = 4.dp,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(140.dp, 200.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .placeholder(
                                visible = true,
                                color = Color.Red.copy(alpha = 0.7f),
                            )
                            .padding(8.dp)
                            .clickable(onClick = {})
                    ) {

                    }
                }
            }
            is HomeUiState.Loading -> {
                LazyRow {
                    items(List(8) { 0 }) {
                        Card(
                            elevation = 4.dp,
                            modifier = Modifier
                                .padding(4.dp)
                                .size(140.dp, 200.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .placeholder(
                                    visible = true,
                                    color = Color.Gray,
                                    highlight = PlaceholderHighlight.fade(),
                                )
                                .padding(8.dp)
                                .clickable(onClick = {})
                        ) {

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CharactersColumnSection(state: HomeUiState, listState: LazyListState) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "CharactersColumn",
            color = MaterialTheme.colors.onSurface,
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold)
        Spacer(modifier = Modifier.height(8.dp))
        when (state) {
            is HomeUiState.Success -> {
                LazyColumn(state = listState) {
                    items(state.data) { character ->
                        Card(
                            elevation = 4.dp,
                            modifier = Modifier
                                .padding(2.dp)
                                .fillMaxWidth()
                                .height(100.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .padding(8.dp)
                                .clickable(onClick = {})
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
                                Text(
                                    text = character.name,
                                    fontSize = 14.sp,
                                    modifier = Modifier.padding(horizontal = 20.dp)
                                )
                            }
                        }
                    }
                }
            }
            is HomeUiState.Error -> {
                LazyColumn {
                    items(List(20) { 0 }) {
                        Card(
                            elevation = 4.dp,
                            modifier = Modifier
                                .padding(4.dp)
                                .fillMaxWidth()
                                .height(100.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .placeholder(
                                    visible = true,
                                    color = Color.Red.copy(alpha = 0.7f),
                                    highlight = PlaceholderHighlight.fade(),
                                )
                                .padding(8.dp)
                                .clickable(onClick = {})
                        ) {

                        }
                    }
                }
            }
            is HomeUiState.Loading ->
                LazyColumn {
                    items(List(20) { 0 }) {
                        Card(
                            elevation = 4.dp,
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth()
                                .height(100.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .placeholder(
                                    visible = true,
                                    color = Color.Gray,
                                    highlight = PlaceholderHighlight.fade(),
                                )
                                .clickable(onClick = {})
                        ) {

                        }
                    }
                }
        }
    }
}