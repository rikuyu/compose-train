package com.example.ui.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Add
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Routes(val route: String) {

    object Grid : Routes("grid")

    object Favorite : Routes("favorite")

    object Catalog : Routes("catalog")

    object DetailCharacter : Routes("detail") {
        fun createRoute(id: Int) = "${this.route}/$id"
    }

    object LogIn : Routes("login")

    object SignUp : Routes("signup")

    object Todo : Routes("todo")

    object CreateTodo : Routes("create")

    object UpdateTodo : Routes("update") {
        fun createRoute(id: String) = "${this.route}/$id"
    }
}

enum class BottomNavigationItem(val label: String, val icon: ImageVector) {
    GRID("Grid", Icons.Filled.Home),
    TODO("Todo", Icons.Outlined.Add),
    CATALOG("Catalog", Icons.Filled.Search),
}
