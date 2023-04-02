package com.example.ui.todo.todo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.model.Todo
import com.example.ui.todo.TodoViewModel
import com.example.ui.utils.Routes
import com.example.ui.utils.checkIsTodoBodyValid
import com.example.ui.utils.checkIsTodoTitleValid
import com.example.ui.utils.compose.FullScreenErrorView
import com.example.ui.utils.compose.FullScreenLoadingIndicator

fun NavGraphBuilder.addUpdateTodo(navController: NavController) {
    composable(
        route = "${Routes.UpdateTodo.route}/{id}",
        arguments = listOf(
            navArgument("id") {
                type = NavType.StringType
                nullable = false
            }
        )
    ) {
        UpdateTodoScreen(
            modifier = Modifier.padding(4.dp),
            navController = navController,
            id = it.arguments?.getString("id")
        )
    }
}

@Composable
fun UpdateTodoScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    id: String?,
    viewModel: TodoViewModel = hiltViewModel()
) {
    if (id == null) {
        FullScreenErrorView()
    } else {
        LaunchedEffect(Unit) {
            viewModel.getTodo(id)
        }

        val oldTodo by viewModel.todo.collectAsStateWithLifecycle()

        oldTodo.StateView(
            loadingView = { FullScreenLoadingIndicator() },
            errorView = { FullScreenErrorView() },
            successView = { UpdateContent(modifier, navController, it, viewModel) }
        )
    }
}

@Composable
fun UpdateContent(
    modifier: Modifier = Modifier,
    navController: NavController,
    todo: Todo,
    viewModel: TodoViewModel
) {
    var title by remember { mutableStateOf(todo.title) }

    var body by remember { mutableStateOf(todo.body) }

    var isImportant by remember { mutableStateOf(todo.isImportant) }

    var isTodoTitleValid by remember { mutableStateOf(checkIsTodoTitleValid(title)) }

    var isTodoBodyValid by remember { mutableStateOf(checkIsTodoBodyValid(body)) }

    Column(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                    isTodoTitleValid = checkIsTodoTitleValid(title)
                },
                label = {
                    Text(
                        text = "New Todo Title",
                        style = MaterialTheme.typography.body2
                    )
                },
                singleLine = true,
                modifier = modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.body1,
                isError = !isTodoTitleValid,
            )
            OutlinedTextField(
                value = body,
                onValueChange = {
                    body = it
                    isTodoBodyValid = checkIsTodoBodyValid(body)
                },
                label = {
                    Text(
                        text = "New Todo Body",
                        style = MaterialTheme.typography.body2
                    )
                },
                singleLine = true,
                modifier = modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.body1,
                isError = !isTodoBodyValid,
            )
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Important"
                )
                Checkbox(
                    checked = isImportant,
                    onCheckedChange = { isImportant = it },
                    colors = CheckboxDefaults.colors(MaterialTheme.colors.primary)
                )
            }
        }
        Row(
            modifier = modifier
                .background(MaterialTheme.colors.background)
                .padding(start = 12.dp, end = 12.dp, top = 12.dp, bottom = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    viewModel.updateTodo(todo.id, title, body, isImportant)
                    navController.navigate(Routes.Todo.route)
                },
                enabled = isTodoTitleValid && isTodoBodyValid,
                colors = if (isTodoTitleValid && isTodoBodyValid) ButtonDefaults.textButtonColors(
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = Color.White,
                ) else ButtonDefaults.textButtonColors(
                    backgroundColor = Color.LightGray,
                    contentColor = Color.DarkGray,
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20)
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 4.dp),
                    text = "Update",
                    fontSize = 18.sp
                )
            }
        }
    }
}