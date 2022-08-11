package com.example.ui.todo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.ui.utils.Routes
import com.example.ui.utils.collectAsStateWithLifecycle

fun NavGraphBuilder.addTodo(
    changeScreen: () -> Unit,
) {
    composable(route = Routes.Todo.route) {
        changeScreen()
        TodoScreen()
    }
}

@Composable
fun TodoScreen(
    viewModel: TodoViewModel = hiltViewModel(),
) {
    val todos by viewModel.todos.collectAsStateWithLifecycle()

    var query by remember { mutableStateOf("") }

    LaunchedEffect(query) {
        viewModel.getFilteredList(query)
    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp)) {
        TopSearchBar(query = query) { query = it }
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            items(todos, key = { it.id }) {
                TodoListItem(todo = it)
            }
        }
    }
}