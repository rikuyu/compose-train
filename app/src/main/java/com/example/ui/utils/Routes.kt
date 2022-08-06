package com.example.ui.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Add
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Routes(val route: String, val title: String? = null) {

    object Home : Routes("home")

    object ColumnRow : Routes("colrow", "Home")

    object Grid : Routes("grid", "Home")

    object Favorite : Routes("favorite", "Favorite")

    object Profile : Routes("profile")

    object DetailCharacter : Routes("character_detail", "Detail") {
        fun createRoute(id: Int) = "${this.route}/$id"
    }

    object Todo : Routes("todo")

    object EditProfile : Routes("edit_profile")
}

enum class BottomNavigationItem(val label: String, val icon: ImageVector) {
    COLUMN_ROW("ColRow", Icons.Filled.Home),
    TODO("Todo", Icons.Outlined.Add),
    PROFILE("Profile", Icons.Filled.Person)
}