package com.example.feature.museum.animicons

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
internal fun ShakeIcon() {
    val shakeAngle = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        delay(100)
        shakeAngle.animateTo(targetValue = 25f, animationSpec = tween(120))
        shakeAngle.animateTo(targetValue = -25f, animationSpec = tween(250))
        shakeAngle.animateTo(targetValue = 18.75f, animationSpec = tween(200))
        shakeAngle.animateTo(targetValue = -12.50f, animationSpec = tween(150))
        shakeAngle.animateTo(targetValue = 6.25f, animationSpec = tween(100))
        shakeAngle.animateTo(targetValue = 0f, animationSpec = tween(50))
    }

    Box(modifier = Modifier.padding(16.dp)) {
        Icon(
            imageVector = Icons.Filled.Lock,
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .rotate(shakeAngle.value)
                .align(Alignment.Center),
            tint = Color.Blue,
        )
    }
}
