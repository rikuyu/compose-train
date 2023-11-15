package com.example.feature.museum.spotlight

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import showToast

@Composable
fun SpotLight(
    targetSize: Int,
    targetObject: Rect,
) {
    val context = LocalContext.current
    var isSpot by remember { mutableStateOf(false) }
    val animatedRadius by animateFloatAsState(
        targetValue = if (isSpot) targetSize * 2f + 16f else 0f,
        animationSpec = tween(500),
        label = "",
    )

    LaunchedEffect(Unit) {
        delay(1000)
        isSpot = true
    }
    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = isSpot,
            modifier = Modifier.fillMaxSize(),
            enter = fadeIn(animationSpec = tween(500)),
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = { offset ->
                                if (targetObject.contains(offset)) {
                                    context.showToast("${targetIndex + 1}")
                                }
                            },
                        )
                    },
            ) {
                val spotLightPath = Path().apply { addOval(Rect(targetObject.center, animatedRadius)) }
                clipPath(
                    path = spotLightPath,
                    clipOp = ClipOp.Difference,
                ) {
                    drawRect(
                        color = Color.Black.copy(alpha = 0.7f),
                        size = Size(size.width, size.height),
                    )
                }
            }
        }
    }
}

private const val PADDING = 16
private const val LIST_SIZE = 15
private val targetIndex = (0 until LIST_SIZE).random()

@Composable
fun SampleSpotLightScreen() {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val boxSize = (screenWidth - PADDING * 4) / 3
    var targetObject by remember { mutableStateOf<Rect?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(PADDING.dp),
            horizontalArrangement = Arrangement.spacedBy(PADDING.dp),
            modifier = Modifier.padding(PADDING.dp),
        ) {
            items(
                items = List(LIST_SIZE) { it },
                key = { it.hashCode() },
            ) { num ->
                val color = getRandomColor()
                val isWhite = color.luminance() > 0.5
                Box(
                    modifier = Modifier
                        .size(boxSize.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(color)
                        .onGloballyPositioned { layoutCoordinates ->
                            if (num == targetIndex) {
                                val rect = layoutCoordinates.boundsInRoot()
                                targetObject = rect
                            }
                        },
                ) {
                    Text(
                        text = "${num + 1}",
                        fontSize = 20.sp,
                        color = if (isWhite) Color.Black else Color.White,
                        modifier = Modifier.align(Alignment.Center),
                    )
                }
            }
        }
        if (targetObject != null) {
            SpotLight(
                targetSize = boxSize,
                targetObject = targetObject!!,
            )
        }
    }
}

private fun getRandomColor() = listOf(
    Color.Black,
    Color.Blue,
    Color.Yellow,
    Color.Gray,
    Color.LightGray,
    Color.DarkGray,
    Color.Green,
    Color.Red,
    Color.Magenta,
    Color.Cyan,
).random()
