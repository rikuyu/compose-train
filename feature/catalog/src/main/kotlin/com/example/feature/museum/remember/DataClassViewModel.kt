package com.example.feature.museum.remember

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

internal data class UiState(
    val flag: Boolean = false,
    val count: Int = 0,
)

internal class DataClassViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState

    fun setFlagTrue() {
        _uiState.value = _uiState.value.copy(flag = true)
    }

    fun setFlagFalse() {
        _uiState.value = _uiState.value.copy(flag = false)
    }

    fun countUp() {
        _uiState.value = _uiState.value.copy(count = _uiState.value.count + 1)
    }
}
