package com.example.feature.museum.snapshotFlow

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SnapshotFlowViewModel : ViewModel() {
    private val _count = MutableStateFlow(Count(0))
    val count = _count.asStateFlow()

    private val _countInstance = MutableStateFlow(Count(0))
    val countInstance = _countInstance.asStateFlow()

    init {
        countUp()
    }

    private fun countUp() {
        viewModelScope.launch {
            repeat(5) {
                val oldCount = _count.value
                _count.value = oldCount.copy(count = oldCount.count + 1)

                val oldCountInstance = _countInstance.value
                _countInstance.value = Count(oldCountInstance.count + 1)

                delay(1000)
                Log.d("SnapshotFlowActivity", "=====================")
            }
        }
    }
}
