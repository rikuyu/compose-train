package com.example.feature.museum.canvas

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp

@Composable
fun FacebookIcon() {
    val paint = Paint().apply {
        textAlign = Paint.Align.CENTER
        textSize = 200f
        color = Color.White.toArgb()
    }
    Canvas(
        modifier = Modifier
            .size(100.dp)
            .padding(16.dp),
    ) {
        drawRoundRect(
            color = Color(0xFF1776D1),
            cornerRadius = CornerRadius(40f, 40f),
            style = Fill
        )
        drawContext.canvas.nativeCanvas.drawText("f", center.x + 25, center.y + 80, paint)
    }
}
