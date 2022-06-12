package com.example.composetrainapp.ui.utils

import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.MutableStateFlow

data class UiState<T>(
    val isLoading: Boolean = false,
    val data: T? = null,
    val error: Throwable? = null,
) {
    @Composable
    fun StateView(
        loadingView: (@Composable () -> Unit)?,
        errorView: ((error: Throwable) -> Unit)?,
        successView: @Composable (data: T) -> Unit,
    ) {
        if (isLoading && data == null) {
            if (loadingView != null) loadingView()
        } else if (error != null && data == null) {
            if (errorView != null) errorView(error)
        } else if (data != null) {
            successView(data)
        }
    }
}

fun <T> MutableStateFlow<UiState<T>>.startLoading() {
    value = value.copy(isLoading = true)
}

fun <T> MutableStateFlow<UiState<T>>.handleData(data: T) {
    value = value.copy(
        isLoading = false,
        data = data,
        error = null
    )
}

fun <T> MutableStateFlow<UiState<T>>.handleError(error: Throwable) {
    value = value.copy(
        isLoading = false,
        error = error
    )
}
