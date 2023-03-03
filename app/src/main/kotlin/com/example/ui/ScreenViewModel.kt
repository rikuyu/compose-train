package com.example.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.ui.utils.Routes

class ScreenViewModel : ViewModel() {

    private val _screen: MutableState<Routes> = mutableStateOf(Routes.Grid)
    val screen: State<Routes> = _screen

    fun setScreen(screen: Routes) {
        _screen.value = screen
    }
}