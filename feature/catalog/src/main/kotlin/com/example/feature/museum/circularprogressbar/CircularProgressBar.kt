package com.example.feature.museum.circularprogressbar

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateInt
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.withSaveLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.ColorUtils

@Composable
internal fun CircularProgressBar(
    modifier: Modifier = Modifier,
) {
    var transitionState by remember { mutableStateOf(false) }
    val transition = updateTransition(targetState = transitionState, label = "")

    val current = 0.75f
    val colors = listOf(
        Color(0xFF3B91C4),
        Color(0xFFF84CAD),
    )
    val progressTextColor = Color(
        ColorUtils.blendARGB(colors[0].toArgb(), colors[1].toArgb(), current),
    )
    val textColor by transition.animateColor(
        transitionSpec = { tween(durationMillis = 2000) },
        targetValueByState = { if (it) progressTextColor else colors[0] },
        label = "",
    )
    val progress by transition.animateInt(
        transitionSpec = { tween(durationMillis = 2000) },
        targetValueByState = { if (it) (100 * current).toInt() else 0 },
        label = "",
    )
    val angle by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 2000) },
        targetValueByState = { if (it) 360f * current else 0f },
        label = "",
    )
    val progressText = buildAnnotatedString {
        append("$progress")
        withStyle(style = SpanStyle(fontSize = 40.sp)) {
            append("%")
        }
    }
    val density = LocalDensity.current

    LaunchedEffect(Unit) { transitionState = true }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(400.dp)
            .drawWithCache {
                val paint = Paint()
                val brush = Brush.sweepGradient(colors)
                val strokeWidth = with(density) { 80.dp.toPx() }
                onDrawWithContent {
                    drawContent()
                    drawIntoCanvas { canvas ->
                        canvas.withSaveLayer(bounds = size.toRect(), paint) {
                            drawCircle(
                                color = Color.LightGray,
                                radius = size.minDimension / 2 - strokeWidth / 2,
                                style = Stroke(width = strokeWidth),
                            )
                            rotate(degrees = -90f) {
                                drawArc(
                                    brush = brush,
                                    startAngle = 0f,
                                    sweepAngle = angle,
                                    useCenter = true,
                                    blendMode = BlendMode.SrcAtop,
                                )
                            }
                        }
                    }
                }
            },
    ) {
        Text(
            text = progressText,
            style = TextStyle(fontSize = 80.sp),
            color = textColor,
        )
    }
}
