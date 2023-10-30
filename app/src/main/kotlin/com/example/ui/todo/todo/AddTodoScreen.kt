package com.example.ui.todo.todo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.ui.todo.TodoViewModel
import com.example.ui.utils.Routes
import com.example.ui.utils.checkIsTodoBodyValid
import com.example.ui.utils.checkIsTodoTitleValid

fun NavGraphBuilder.addAddTodo(navController: NavController) {
    composable(route = Routes.CreateTodo.route) {
        AddTodoScreen(modifier = Modifier.padding(4.dp), navController)
    }
}

@Composable
fun AddTodoScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: TodoViewModel = hiltViewModel(),
) {
    var title by remember { mutableStateOf("") }

    var body by remember { mutableStateOf("") }

    var isImportant by remember { mutableStateOf(false) }

    var isTodoTitleValid by remember { mutableStateOf(false) }

    var isTodoBodyValid by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                    isTodoTitleValid = checkIsTodoTitleValid(title)
                },
                label = {
                    Text(
                        text = "Todo Title",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                },
                singleLine = true,
                modifier = modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodyLarge,
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
                        text = "Todo Body",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                },
                singleLine = true,
                modifier = modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodyLarge,
                isError = !isTodoBodyValid,
            )
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Important",
                )
                Checkbox(
                    checked = isImportant,
                    onCheckedChange = { isImportant = it },
                    colors = CheckboxDefaults.colors(MaterialTheme.colorScheme.primary),
                )
            }
        }
        Row(
            modifier = modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(start = 12.dp, end = 12.dp, top = 12.dp, bottom = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(
                onClick = {
                    viewModel.addTodo(title, body, isImportant)
                    navController.navigate(Routes.Todo.route)
                },
                enabled = isTodoTitleValid && isTodoBodyValid,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20),
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 4.dp),
                    text = "Add",
                    fontSize = 18.sp,
                )
            }
        }
    }
}
