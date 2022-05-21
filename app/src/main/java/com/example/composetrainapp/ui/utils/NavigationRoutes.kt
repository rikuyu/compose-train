package com.example.composetrainapp.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Add
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationRoutes(val route: String) {

    object Home : NavigationRoutes("home")

    object Profile : NavigationRoutes("profile")

    object Todo : NavigationRoutes("todo")

    object EditProfile : NavigationRoutes("edit_profile")

    object Detail : NavigationRoutes("detail")
}

enum class BottomNavigationItem(val label: String, val icon: ImageVector) {
    HOME("Home", Icons.Filled.Home),
    TODO("Todo", Icons.Outlined.Add),
    PROFILE("Profile", Icons.Filled.Person)
}

