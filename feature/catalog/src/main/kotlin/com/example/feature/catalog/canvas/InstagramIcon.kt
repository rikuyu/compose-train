package com.example.feature.catalog.canvas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun InstagramIcon() {
    val colors = listOf(Color.Yellow, Color.Red, Color.Magenta)
    val brush = Brush.linearGradient(colors = colors)

    Canvas(
        modifier = Modifier
            .size(100.dp)
            .padding(16.dp),
    ) {
        drawRoundRect(
            brush = brush,
            cornerRadius = CornerRadius(70f, 70f),
            style = Stroke(width = 18f, cap = StrokeCap.Round),
        )
        drawCircle(
            brush = brush,
            radius = size.width / 4,
            style = Stroke(width = 18f, cap = StrokeCap.Round),
        )
        drawCircle(
            brush = brush,
            radius = 16f,
            style = Fill,
            center = Offset(size.width * .8f, size.height * .2f),
        )
    }
}
