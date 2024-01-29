package com.example.feature.museum.circularprogressbar

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateInt
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun CircularProgressBar(
    modifier: Modifier = Modifier,
    currentValue: Int = 75,
    maxValue: Int = 100,
    progressBackgroundColor: Color = Color.Blue.copy(alpha = 0.2f),
    progressIndicatorColor: Color = Color.Blue,
) {
    var startAnimation by remember { mutableStateOf(false) }
    val transition = updateTransition(targetState = startAnimation, label = "")

    val barAnim by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 2500) },
        label = "",
    ) { if (it) currentValue / maxValue.toFloat() else 0f }

    val progress by transition.animateInt(
        transitionSpec = { tween(durationMillis = 2500) },
        label = "",
    ) {
        if (it) currentValue else 0
    }

    LaunchedEffect(Unit) { startAnimation = true }

    Box(modifier = modifier) {
        val canvasSize = 200
        val stroke = with(LocalDensity.current) {
            Stroke(
                width = 4.dp.toPx(),
                cap = StrokeCap.Round,
                join = StrokeJoin.Round,
            )
        }
        Surface(
            color = Color.Red,
            shape = CircleShape,
            modifier = Modifier
                .size((canvasSize - stroke.width).dp)
                .align(Alignment.Center),
            content = {},
        )

        Canvas(Modifier.size(canvasSize.dp)) {
            val startAngle = 90f
            val sweep = barAnim * 360f
            val diameterOffset = stroke.width / 2
            val arcRadius = size.width - 2 * diameterOffset

            drawArc(
                color = progressBackgroundColor,
                startAngle = 360f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = Offset(diameterOffset, diameterOffset),
                size = Size(arcRadius.toDp().toPx(), arcRadius.toDp().toPx()),
                style = stroke,
            )

            drawArc(
                color = progressIndicatorColor,
                startAngle = startAngle,
                sweepAngle = sweep,
                useCenter = false,
                topLeft = Offset(diameterOffset, diameterOffset),
                size = Size(arcRadius.toDp().toPx(), arcRadius.toDp().toPx()),
                style = stroke,
            )
        }

        Surface(
            color = Color.Black,
            shape = CircleShape,
            modifier = Modifier
                .widthIn(min = 90.dp)
                .offset(y = 8.dp)
                .align(Alignment.BottomCenter),
        ) {
            Text(
                text = "${progress}%完了",
                color = Color.White,
                modifier = Modifier
                    .padding(
                        horizontal = 12.dp,
                        vertical = 4.dp,
                    ),
            )
        }
    }
}
