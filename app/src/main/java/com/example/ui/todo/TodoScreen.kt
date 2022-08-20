package com.example.ui.todo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.data.utils.Result
import com.example.ui.utils.Routes
import com.example.ui.utils.collectAsStateWithLifecycle

fun NavGraphBuilder.addTodo(
    navController: NavController,
    changeScreen: () -> Unit,
) {
    composable(route = Routes.Todo.route) {
        changeScreen()
        TodoScreen(navController)
    }
}

@Composable
fun TodoScreen(
    navController: NavController,
    viewModel: TodoViewModel = hiltViewModel(),
) {
    val todos by viewModel.todos.collectAsStateWithLifecycle()

    var query by remember { mutableStateOf("") }

    LaunchedEffect(query) {
        viewModel.getFilteredList(query)
    }

    when (todos) {
        is Result.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize()
            ) {
                CircularProgressIndicator()
            }
        }
        is Result.Error -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize()
            ) {
                TextButton(onClick = {
                    viewModel.getAllTodo()
                }) {
                    Text(text = "Retry")
                }
            }
        }
        is Result.Success -> {
            val list = (todos as Result.Success).data
            if (list.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = buildAnnotatedString {
                        append("Todoがありません")
                        append("追加してください")
                    })
                    OutlinedButton(
                        onClick = { navController.navigate(Routes.AddTodo.route) }
                    ) {
                        Text(text = "Add")
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    TopSearchBar(query = query) { query = it }
                    Spacer(modifier = Modifier.height(10.dp))
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        items((todos as Result.Success).data, key = { it.id ?: it.title }) {
                            TodoListItem(todo = it)
                        }
                    }
                }
            }
        }
    }
}