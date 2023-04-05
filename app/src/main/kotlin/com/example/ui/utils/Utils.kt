package com.example.ui.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.ui.graphics.Color
import com.example.ui.todo.InputState
import java.util.regex.Pattern

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

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT)
        .show()
}

private const val EMAIL_VALIDATION_REGEX = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.+[a-z]+$"

private const val PASSWORD_VALIDATION_REGEX = "[a-z0-9]{6,10}"

fun checkIsEmailValid(email: String) = Pattern.matches(EMAIL_VALIDATION_REGEX, email)

fun checkIsPasswordValid(password: String) = Pattern.matches(PASSWORD_VALIDATION_REGEX, password)

fun getInputState(isValid: Boolean) = if (isValid) InputState.Valid else InputState.NotValid

fun checkIsNameValid(name: String) = name.length in 2..5

fun checkIsTodoTitleValid(title: String) = title.length in 2..10

fun checkIsTodoBodyValid(body: String) = body.length in 3..50