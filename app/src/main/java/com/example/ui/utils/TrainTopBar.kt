package com.example.ui.utils

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TrainTopBar(
    screen: Routes,
    navController: NavController,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
) {
    var expanded by remember { mutableStateOf(false) }

    if (
        screen == Routes.Grid ||
        screen == Routes.DetailCharacter ||
        screen == Routes.Favorite
    ) TopAppBar(
        title = { Text(text = screen.title ?: "") },
        navigationIcon = {
            if (screen == Routes.DetailCharacter || screen == Routes.AddTodo || screen == Routes.UpdateTodo) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = null)
                }
            } else {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Filled.Menu, contentDescription = null)
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(onClick = {
                        expanded = false
                        if (screen != Routes.Grid)
                            navController.navigate(Routes.Grid.route)
                    }) {
                        Text(text = "Grid")
                    }
                    Divider()
                    DropdownMenuItem(onClick = {
                        expanded = false
                        if (screen != Routes.Favorite)
                            navController.navigate(Routes.Favorite.route)
                    }) {
                        Text(text = "Favorite")
                    }
                    Divider()
                    DropdownMenuItem(onClick = {
                        expanded = false
                        scope.launch {
                            showSnackBar(scaffoldState, "Sample SnackBar", "Do")
                        }
                    }) {
                        Text(text = "SnackBar")
                    }
                }
            }
        }
    )
}