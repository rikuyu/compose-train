package com.example.feature.catalog.score

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ScoreBoard() {

    var displayNumber by remember { mutableStateOf(10) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        CircleButton(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = "Minus",
        ) { displayNumber-- }
        Spacer(modifier = Modifier.width(24.dp))
        AnimatedContent(
            targetState = displayNumber,
            transitionSpec = {
                if (targetState > initialState) {
                    ContentTransform(
                        targetContentEnter = slideInVertically { height -> height } + fadeIn(),
                        initialContentExit = slideOutVertically { height -> -height } + fadeOut()
                    )
                } else {
                    ContentTransform(
                        targetContentEnter = slideInVertically { height -> -height } + fadeIn(),
                        initialContentExit = slideOutVertically { height -> height } + fadeOut()
                    )
                }
            },
            label = "",
        ) { number ->
            Box(modifier = Modifier.width(40.dp)) {
                Text(
                    text = "$number",
                    fontSize = 32.sp,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        Spacer(modifier = Modifier.width(24.dp))
        CircleButton(
            imageVector = Icons.Default.KeyboardArrowUp,
            contentDescription = "Add"
        ) { displayNumber++ }
    }
}

@Composable
fun CircleButton(
    imageVector: ImageVector,
    contentDescription: String = "",
    clickable: Boolean = true,
    click: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .size(40.dp)
            .background(Color.Blue.copy(alpha = 0.1f))
            .clickable(enabled = clickable, onClick = click)
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = Color.Blue,
            modifier = Modifier.size(20.dp)
        )
    }
}
