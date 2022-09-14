package com.example.ui.utils

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun TrainFloatingActionButton(screen: Routes, navController: NavController) {
    if (screen == Routes.Todo)
        FloatingActionButton(
            onClick = { navController.navigate(Routes.AddTodo.route) },
            backgroundColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(Icons.Filled.Add, contentDescription = null, tint = MaterialTheme.colorScheme.surface)
        }
}