package com.example.ui.utils.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.ui.utils.Routes

private val String?.isHome
    get() = this == Routes.Grid.route ||
            this == Routes.Favorite.route ||
            this == Routes.DetailCharacter.route

private val String?.isAddOrUpdateTodo
    get() = this == Routes.AddTodo.route ||
            this == Routes.UpdateTodo.route

private val String?.topBarTitle
    get() = this?.let { substring(0, 1).uppercase() + substring(1).lowercase() } ?: ""

@Composable
fun TrainTopBar(
    navController: NavController,
    scrollBehavior: TopAppBarScrollBehavior
) {
    var expanded by remember { mutableStateOf(false) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val isShowTopBar = currentDestination?.hierarchy?.any { it.route.isHome || it.route.isAddOrUpdateTodo } ?: false
    val isShowArrowBack = currentDestination?.hierarchy?.any {
        it.route == Routes.DetailCharacter.route || it.route.isAddOrUpdateTodo
    } ?: false

    AnimatedVisibility(
        visible = isShowTopBar,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        TopAppBar(
            title = { Text(text = currentDestination?.route.topBarTitle) },
            navigationIcon = {
                if (isShowArrowBack) {
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
                            if (
                                currentDestination?.hierarchy?.any {
                                    it.route == Routes.Grid.route
                                } == true
                            )
                                navController.navigate(Routes.Grid.route)
                        }) {
                            Text(text = "Grid")
                        }
                        Divider()
                        DropdownMenuItem(onClick = {
                            expanded = false
                            if (
                                currentDestination?.hierarchy?.any {
                                    it.route == Routes.Favorite.route
                                } == true
                            )
                                navController.navigate(Routes.Favorite.route)
                        }) {
                            Text(text = "Favorite")
                        }
                    }
                }
            },
            scrollBehavior = scrollBehavior
        )
    }
}