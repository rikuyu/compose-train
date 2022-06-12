package com.example.composetrainapp.ui.utils

import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.MutableStateFlow

data class UiState<T>(
    val isLoading: LoadingState = LoadingState.NOT_LOADING,
    val data: T? = null,
    val error: Throwable? = null,
) {
    @Composable
    fun StateView(
        loadingView: (@Composable () -> Unit)?,
        errorView: ((error: Throwable) -> Unit)?,
        successView: @Composable (data: T) -> Unit,
    ) {
        if (isLoading == LoadingState.LOADING) {
            if (loadingView != null) loadingView()
        } else if (error != null) {
            if (errorView != null) errorView(error)
        } else if (data != null) {
            successView(data)
        }
    }

    val isRefreshing: Boolean get() = isLoading == LoadingState.REFRESHING
}

fun <T> MutableStateFlow<UiState<T>>.startLoading(loadingState: LoadingState) {
    value = value.copy(isLoading = loadingState)
}

fun <T> MutableStateFlow<UiState<T>>.handleData(data: T) {
    value = value.copy(
        isLoading = LoadingState.NOT_LOADING,
        data = data,
        error = null
    )
}

fun <T> MutableStateFlow<UiState<T>>.handleError(error: Throwable) {
    value = value.copy(
        isLoading = LoadingState.NOT_LOADING,
        error = error
    )
}

enum class LoadingState {
    LOADING,
    NOT_LOADING,
    REFRESHING,
}
