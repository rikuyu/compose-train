package com.example.ui.utils.compose

import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.ui.utils.Routes

@Composable
fun TrainTopBar(screen: Routes, navController: NavController) {
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
                }
            }
        }
    )
}