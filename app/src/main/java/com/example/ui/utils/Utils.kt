package com.example.ui.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.example.composetrainapp.R
import kotlinx.coroutines.flow.StateFlow

@Composable
fun <T> StateFlow<T>.collectAsStateWithLifecycle(
    lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
): State<T> {
    val initialValue = remember(this) { this.value }
    return produceState(
        key1 = this,
        key2 = lifecycle,
        key3 = minActiveState,
        initialValue = initialValue
    ) {
        lifecycle.repeatOnLifecycle(minActiveState) {
            collect {
                Log.d("FLOW_COLLECT_TEST_TAG", "WithLifecycle: $value")
                this@produceState.value = it
            }
        }
    }
}

fun getGenderRes(genderType: String): Pair<Int, Color> = when (genderType) {
    "Male" -> Pair(R.drawable.ic_male, Color(0xFF82BDE9))
    "Female" -> Pair(R.drawable.ic_female, Color(0xFFE982D2))
    else -> Pair(R.drawable.ic_unknown_data, Color.LightGray)
}

fun getCreatureRes(creatureType: String): Pair<Int, Color> = when (creatureType) {
    "Human" -> Pair(R.drawable.ic_human, Color(0xFFEFC2A1))
    "Alien" -> Pair(R.drawable.ic_alien, Color(0xFFA1EFA2))
    else -> Pair(R.drawable.ic_unknown_data, Color.LightGray)
}

fun getStatusRes(status: String): Pair<Int, Color> = when (status) {
    "Alive" -> Pair(R.drawable.ic_status_alive, Color.LightGray)
    "Dead" -> Pair(R.drawable.ic_status_dead, Color.Red)
    else -> Pair(R.drawable.ic_unknown_data, Color.LightGray)
}

suspend fun showSnackBar(
    scaffoldState: ScaffoldState,
    message: String,
    actionLabel: String? = null,
    action: (() -> Unit)? = null,
) {
    when (
        scaffoldState.snackbarHostState.showSnackbar(
            message = message,
            actionLabel = actionLabel
        )
    ) {
        SnackbarResult.ActionPerformed -> action?.invoke()
        SnackbarResult.Dismissed -> {}
    }
}

suspend fun showSnackBarWithArg(
    scaffoldState: ScaffoldState,
    message: String,
    actionLabel: String,
    arg: Int,
    action: ((Int) -> Unit),
) {
    when (
        scaffoldState.snackbarHostState.showSnackbar(
            message = message,
            actionLabel = actionLabel
        )
    ) {
        SnackbarResult.ActionPerformed -> action.invoke(arg)
        SnackbarResult.Dismissed -> {}
    }
}

fun getBackgroundColor(genderType: String?) = when (genderType) {
    "Male" -> Color(0xFF82BDE9)
    "Female" -> Color(0xFFE982D2)
    else -> Color.LightGray
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT)
        .show()
}