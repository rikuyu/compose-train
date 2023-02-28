package com.example.ui.utils.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ToggleButton(
    isClicked: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    backgroundColor: Color = Color.Transparent,
    iconColor: Color = Color.Transparent,
    size: Dp = 48.dp,
    iconSize: Dp = size / 2,
    clickedIconVector: ImageVector,
    notClickedIconVector: ImageVector,
    onClicked: ((Boolean) -> Unit)? = null,
) {
    Box(
        modifier = modifier.toggleButton(onClicked, isClicked, enabled, backgroundColor, size),
        contentAlignment = Alignment.Center
    ) {
        if (isClicked) {
            Icon(
                imageVector = clickedIconVector,
                contentDescription = null,
                modifier = Modifier.size(iconSize),
                tint = iconColor
            )
        } else {
            Icon(
                imageVector = notClickedIconVector,
                contentDescription = null,
                modifier = Modifier.size(iconSize),
                tint = iconColor
            )
        }
    }
}

private fun Modifier.toggleButton(
    onClickedChange: ((Boolean) -> Unit)?,
    clicked: Boolean,
    enabled: Boolean,
    background: Color,
    size: Dp,
): Modifier = composed {
    val boxModifier = if (onClickedChange != null) {
        val interactionSource = remember { MutableInteractionSource() }
        val ripple = rememberRipple(bounded = false, radius = 24.dp)
        this
            .toggleable(
                value = clicked,
                onValueChange = { onClickedChange(!clicked) },
                enabled = enabled,
                role = Role.Checkbox,
                interactionSource = interactionSource,
                indication = ripple
            )
    } else {
        this
    }
    boxModifier
        .clip(CircleShape)
        .background(background)
        .size(size)
}