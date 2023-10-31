package com.example.ui.utils.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.material3.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import Routes

private val String?.isHome
    get() = this == Routes.Grid.route ||
        this == Routes.Favorite.route ||
        this?.startsWith(Routes.DetailCharacter.route) ?: false

private val String?.isAddOrUpdateTodo
    get() = this == Routes.CreateTodo.route ||
        this == Routes.UpdateTodo.route ||
        this == Routes.LogIn.route ||
        this == Routes.SignUp.route

private val String?.topBarTitle
    get() = this?.split("/")?.let {
        it[0].substring(0, 1).uppercase() + it[0].substring(1).lowercase()
    } ?: ""

@Composable
fun TrainTopBar(
    navController: NavController,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    var expanded by remember { mutableStateOf(false) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val isShowTopBar = currentDestination?.hierarchy?.any {
        it.route.isHome || it.route.isAddOrUpdateTodo
    } ?: false
    val isShowArrowBack = currentDestination?.hierarchy?.any {
        it.route?.startsWith(Routes.DetailCharacter.route) ?: false || it.route.isAddOrUpdateTodo
    } ?: false

    AnimatedVisibility(
        visible = isShowTopBar,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        TopAppBar(
            title = {
                Text(
                    text = currentDestination?.route.topBarTitle,
                    color = MaterialTheme.colorScheme.primary,
                )
            },
            navigationIcon = {
                if (isShowArrowBack) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    }
                } else {
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(MaterialTheme.colorScheme.background),
                    ) {
                        DropdownMenuItem(onClick = {
                            expanded = false
                            if (
                                currentDestination?.hierarchy?.any {
                                    it.route == Routes.Grid.route
                                } == false
                            ) {
                                navController.navigate(Routes.Grid.route)
                            }
                        }) {
                            Text(text = "Grid", color = MaterialTheme.colorScheme.primary)
                        }
                        Divider()
                        DropdownMenuItem(onClick = {
                            expanded = false
                            if (
                                currentDestination?.hierarchy?.any {
                                    it.route == Routes.Favorite.route
                                } == false
                            ) {
                                navController.navigate(Routes.Favorite.route)
                            }
                        }) {
                            Text(text = "Favorite", color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            },
            scrollBehavior = scrollBehavior,
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        )
    }
}
