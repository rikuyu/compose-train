package com.example.feature.museum.coroutinetimer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal data class CoroutineTimerUiState(
    val time1: Int = 10,
    val time2: Int = 15,
)

internal class CoroutineTimerViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CoroutineTimerUiState())
    val uiState = _uiState.asStateFlow()

    fun startTimer1() {
        viewModelScope.launch {
            while (true) {
                delay(1000L)
                _uiState.update { it.copy(time1 = it.time1 - 1) }
                if (_uiState.value.time1 == 0) break
            }
        }
    }

    fun startTimer2() {
        viewModelScope.launch {
            while (true) {
                delay(1000L)
                _uiState.update { it.copy(time2 = it.time2 - 1) }
                if (_uiState.value.time2 == 0) break
            }
        }
    }
}
