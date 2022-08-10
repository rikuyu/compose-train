package com.example.ui.todo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.ui.utils.Routes

fun NavGraphBuilder.addTodo(
    changeScreen: () -> Unit,
) {
    composable(route = Routes.Todo.route) {
        changeScreen()
        TodoScreen()
    }
}

@Composable
fun TodoScreen() {
    Column(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
        TopSearchBar()
    }
}