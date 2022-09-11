package com.example.ui.todo

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.model.Todo
import com.example.ui.utils.ErrorScreen
import com.example.ui.utils.LoadingScreen
import com.example.ui.utils.Routes

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
    viewModel: TodoViewModel = hiltViewModel()
) {
    val todos by viewModel.todos.collectAsState()

    todos.StateView(
        loadingView = { LoadingScreen() },
        errorView = { ErrorScreen() },
        successView = {
            TodoContent(
                navController = navController,
                filter = viewModel::getFilteredList,
                delete = viewModel::deleteTodo,
                todos = it
            )
        }
    )
}

@Composable
fun TodoContent(
    navController: NavController,
    filter: (String) -> Unit,
    delete: (String) -> Unit,
    todos: List<Todo>
) {
    var query by remember { mutableStateOf("") }

    LaunchedEffect(query) {
        filter(query)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        TopSearchBar(query = query) { query = it }
        Spacer(modifier = Modifier.height(10.dp))
        if (todos.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("Todoがありません\n")
                        append("追加してください")
                    }
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedButton(
                    border = BorderStroke(1.dp, MaterialTheme.colors.primary),
                    onClick = { navController.navigate(Routes.AddTodo.route) }
                ) {
                    Text(text = "Add")
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                items(todos, key = { it.id }) {
                    TodoListItem(todo = it, navController) { id ->
                        delete(id)
                    }
                }
            }
        }
    }
}