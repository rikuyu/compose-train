package com.example.ui.utils.compose

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.semantics.Role
import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource

fun Modifier.debounceClickable(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit
) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "debounceClickable"
        properties["enabled"] = enabled
        properties["onClickLabel"] = onClickLabel
        properties["role"] = role
        properties["onClick"] = onClick
    }
) {
    val throttle = remember(onClick) {
        ComposeThrottle(onClick)
    }

    Modifier.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = LocalIndication.current,
        enabled = enabled,
        onClickLabel = onClickLabel,
        role = role,
        onClick = throttle
    )
}

@OptIn(ExperimentalTime::class)
internal class ComposeThrottle(
    private val event: () -> Unit
) : () -> Unit {
    companion object {
        private var lastClickTime: TimeSource.Monotonic.ValueTimeMark? = null
    }

    override fun invoke() {
        val currentTime = TimeSource.Monotonic.markNow()
        val prevClickTime = lastClickTime
        if (prevClickTime != null) {
            if ((currentTime - prevClickTime).inWholeMilliseconds < 500) {
                return
            }
        }

        lastClickTime = currentTime
        event()
    }
}

