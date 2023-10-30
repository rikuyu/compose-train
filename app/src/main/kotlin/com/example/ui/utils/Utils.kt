package com.example.ui.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.ui.graphics.Color

suspend fun showSnackBar(
    scaffoldState: ScaffoldState,
    message: String,
    actionLabel: String? = null,
    action: (() -> Unit)? = null,
) {
    when (
        scaffoldState.snackbarHostState.showSnackbar(
            message = message,
            actionLabel = actionLabel,
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
            actionLabel = actionLabel,
        )
    ) {
        SnackbarResult.ActionPerformed -> action.invoke(arg)
        SnackbarResult.Dismissed -> {}
    }
}

fun getBackgroundColor(genderType: String?) = when (genderType) {
    "Male" -> Color(0xFF82BDE9)
    "Female" -> Color(0xFFE982D2)
    else -> Color.Gray
}
