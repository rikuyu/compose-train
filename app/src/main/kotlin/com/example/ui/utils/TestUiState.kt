package com.example.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import kotlinx.coroutines.flow.MutableStateFlow

@Stable
data class TestUiState<T>(
    val isLoading: LoadingState = LoadingState.NOT_LOADING,
    val data: T? = null,
    val error: Throwable? = null,
) {
    @Composable
    fun StateView(
        loadingView: (@Composable () -> Unit)?,
        errorView: (@Composable (error: Throwable) -> Unit)?,
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

fun <T> MutableStateFlow<TestUiState<T>>.startLoading(loadingState: LoadingState) {
    value = value.copy(isLoading = loadingState)
}

fun <T> MutableStateFlow<TestUiState<T>>.handleData(data: T) {
    value = value.copy(
        isLoading = LoadingState.NOT_LOADING,
        data = data,
        error = null
    )
}

fun <T> MutableStateFlow<TestUiState<T>>.handleError(error: Throwable) {
    value = value.copy(
        isLoading = LoadingState.NOT_LOADING,
        error = error
    )
}

enum class LoadingState {
    LOADING, NOT_LOADING, REFRESHING,
}