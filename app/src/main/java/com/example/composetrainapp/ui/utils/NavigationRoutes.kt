package com.example.composetrainapp.ui.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Add
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationRoutes(val route: String, val title: String? = null) {

    object Home : NavigationRoutes("home")

    object ColumnRow : NavigationRoutes("colrow", "Home")

    object Grid : NavigationRoutes("grid", "Home")

    object Profile : NavigationRoutes("profile")

    object DetailCharacter : NavigationRoutes("character_detail", "Detail") {
        fun createRoute(id: Int) = "${this.route}/$id"
    }

    object Todo : NavigationRoutes("todo")

    object EditProfile : NavigationRoutes("edit_profile")
}

enum class BottomNavigationItem(val label: String, val icon: ImageVector) {
    COLUMN_ROW("ColRow", Icons.Filled.Home),
    TODO("Todo", Icons.Outlined.Add),
    PROFILE("Profile", Icons.Filled.Person)
}
