package com.example.ui.todo.todo

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.data.utils.Result
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.model.TodoData
import com.example.ui.todo.TodoViewModel
import com.example.ui.utils.compose.FullScreenErrorView
import com.example.ui.utils.compose.FullScreenLoadingIndicator
import com.example.ui.utils.Routes
import com.google.firebase.auth.FirebaseUser

fun NavGraphBuilder.addTodo(
    navController: NavController,
    firebaseUser: FirebaseUser?,
) {
    composable(route = Routes.Todo.route) {
        TodoScreen(navController, firebaseUser)
    }
}

@Composable
fun TodoScreen(
    navController: NavController,
    firebaseUser: FirebaseUser?,
    viewModel: TodoViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.getAllTodo()
        viewModel.getUserData(firebaseUser?.uid)
    }

    val todosData by viewModel.todosData.collectAsStateWithLifecycle()

    when (todosData) {
        is Result.LoadingState -> FullScreenLoadingIndicator()
        is Result.Error -> FullScreenErrorView()
        is Result.Success ->
            TodoContent(
                navController = navController,
                filter = viewModel::getFilteredList,
                delete = viewModel::deleteTodo,
                todosData = (todosData as Result.Success<TodoData>).data,
            )
    }
}

@Composable
fun TodoContent(
    navController: NavController,
    filter: (String) -> Unit,
    delete: (String) -> Unit,
    todosData: TodoData,
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
        verticalArrangement = Arrangement.Top,
    ) {
        TopSearchBar(query = query) { query = it }
        Spacer(modifier = Modifier.height(10.dp))
        if (todosData.todos.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("Todoがありません\n")
                        append("追加してください")
                    },
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedButton(
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                    onClick = { navController.navigate(Routes.CreateTodo.route) },
                ) {
                    Text(text = "Add")
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
            ) {
                items(todosData.todos, key = { it.id }) {
                    TodoListItem(it, todosData.user, navController) { id ->
                        delete(id)
                    }
                }
            }
        }
    }
}
