package com.example.feature.museum.pip

import android.app.PictureInPictureParams
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.util.Rational
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.PictureInPictureModeChangedInfo
import androidx.core.util.Consumer

@Composable
internal fun NotPipScreen() {
    val context = LocalContext.current
    val activity = context.findActivity() ?: return

    PipContent(
        onPipClick = {
            val pipParams = PictureInPictureParams
                .Builder()
                .setAspectRatio(Rational(9, 16))
                .build()
            activity.enterPictureInPictureMode(pipParams)
        },
    )
}

@Composable
internal fun PipScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color.Red,
                        Color.Magenta,
                        Color.Blue,
                        Color.Cyan,
                        Color.Green,
                        Color.Yellow,
                        Color.Red,
                    ),
                ),
            ),
    ) {
        Text(
            text = "Picture In Picture",
            fontSize = 24.sp,
            color = Color.White,
            modifier = Modifier.align(Alignment.Center),
        )
    }
}

@Composable
internal fun rememberIsInPipMode(): Boolean {
    val context = LocalContext.current
    val activity = context.findActivity() ?: return false
    var pipMode by remember { mutableStateOf(activity.isInPictureInPictureMode) }
    DisposableEffect(activity) {
        val observer = Consumer<PictureInPictureModeChangedInfo> { info ->
            pipMode = info.isInPictureInPictureMode
        }
        activity.addOnPictureInPictureModeChangedListener(
            observer,
        )
        onDispose { activity.removeOnPictureInPictureModeChangedListener(observer) }
    }
    return pipMode
}


private fun Context.findActivity(): ComponentActivity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is ComponentActivity) return context
        context = context.baseContext
    }
    return null
}


@Composable
internal fun PipListenerPreApi12() {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
        val context = LocalContext.current
        val activity = context.findActivity() ?: return
        DisposableEffect(context) {
            val pipParams = PictureInPictureParams
                .Builder()
                .build()
            val onUserLeaveListener: () -> Unit = { activity.enterPictureInPictureMode(pipParams) }
            activity.addOnUserLeaveHintListener(onUserLeaveListener)
            onDispose {
                activity.removeOnUserLeaveHintListener(onUserLeaveListener)
            }
        }
    }
}

@Composable
private fun PipContent(
    onPipClick: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row {
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = onPipClick) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null,
                    tint = Color(0xFFA000FC),
                    modifier = Modifier.size(28.dp),
                )
            }
        }
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            itemsIndexed(List(20) { "Item あ い う え お か き く け こ さ し す せ そ" }) { i, text ->
                val color = color(i)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color(i)),
                ) {
                    Text(
                        text = text,
                        color = if (color.luminance() > 0.5) Color.Black else Color.White,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(12.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    HorizontalDivider(modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
}

private val color: (Int) -> Color = { index ->
    when (index % 10) {
        0 -> Color.Blue
        1 -> Color.LightGray
        2 -> Color.Yellow
        3 -> Color.Magenta
        4 -> Color.Cyan
        5 -> Color.Gray
        6 -> Color.Red
        7 -> Color.DarkGray
        8 -> Color.Green
        9 -> Color.Red.copy(alpha = 0.2f)
        else -> Color.Transparent
    }
}
