package com.example.feature.museum.animicons

import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

val heightRatio: (Int) -> Int = { value ->
    when (value) {
        0, 8 -> 3
        1, 7 -> 2
        2, 6 -> 6
        3, 5 -> 5
        4 -> 12
        else -> 1
    }
}

@Composable
internal fun VolumeBars() {
    var isActive by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Row(modifier = Modifier.height(50.dp)) {
            List(9) { it }.forEach {
                VolumeBar(isActive, it)
                if (it != 9) Spacer(modifier = Modifier.width(2.dp))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { isActive = !isActive },
        ) {
            Text(text = if (isActive) "OFF" else "ON")
        }
    }
}

@Composable
fun VolumeBar(
    isActive: Boolean,
    index: Int,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val heightRatio by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (isActive) heightRatio(index).toFloat() else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 350, easing = EaseOut),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "",
    )
    Box(
        modifier = Modifier
            .size(4.dp, 2.dp)
            .scale(1f, heightRatio)
            .clip(CircleShape)
            .background(
                brush = Brush.verticalGradient(
                    listOf(Color(0xFFFF9314), Color(0xFFFA426C)),
                ),
            ),
    ) {
    }
}
