package com.example.ui.todo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.ui.utils.Routes

fun NavGraphBuilder.addAddTodo(
    changeScreen: () -> Unit,
) {
    composable(route = Routes.AddTodo.route) {
        changeScreen()
        AddTodoScreen()
    }
}

@Composable
fun AddTodoScreen(
    viewModel: TodoViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize()
    ) {
        Text("add todo")
    }
}