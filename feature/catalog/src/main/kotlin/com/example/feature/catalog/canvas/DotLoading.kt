package com.example.feature.catalog.canvas

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SquareDotLoading(
    modifier: Modifier = Modifier,
    dotSize: Dp = 16.dp,
    dotColor: Color = Color.Blue,
) {
    val dotSpace = dotSize * 0.4f
    val canvasSize = dotSize * 2 + dotSpace

    val offset = with(LocalDensity.current) { (dotSize + dotSpace).toPx() }
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val xOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = offset,
        animationSpec = infiniteRepeatable(tween(500)),
        label = "",
    )

    Canvas(modifier = modifier.size(canvasSize)) {
        val radius = dotSize.toPx() / 2
        repeat(4) {
            rotate(90f * (it - 1)) {
                drawCircle(
                    color = dotColor,
                    radius = radius,
                    center = Offset(radius + xOffset, radius),
                )
            }
        }
    }
}

@Composable
fun CircleDotLoading(
    modifier: Modifier = Modifier,
    dotNum: Int = 8,
    dotSize: Dp = 12.dp,
    dotColor: Color = Color.Blue,
) {
    val dotSpace = dotSize * 2f
    val canvasSize = dotSize * 2 + dotSpace

    val offset = with(LocalDensity.current) { (dotSize + dotSpace).toPx() }
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val xOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = offset,
        animationSpec = infiniteRepeatable(tween(500)),
        label = "",
    )

    Canvas(modifier = modifier.size(canvasSize)) {
        val radius = dotSize.toPx() / 2
        repeat(dotNum) {
            rotate((360f / dotNum) * (it - 1)) {
                drawCircle(
                    color = dotColor,
                    radius = radius,
                    center = Offset(radius + xOffset, radius),
                )
            }
        }
    }
}
