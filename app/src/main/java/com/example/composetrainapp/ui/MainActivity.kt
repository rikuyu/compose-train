package com.example.composetrainapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.composetrainapp.ui.detail.DetailScreen
import com.example.composetrainapp.ui.home.ColumnRowScreen
import com.example.composetrainapp.ui.home.GridScreen
import com.example.composetrainapp.ui.utils.CustomBottomNavigationBar
import com.example.composetrainapp.ui.utils.NavigationRoutes
import com.example.composetrainapp.ui.utils.showSnackBar
import com.example.composetrainapp.ui.utils.theme.ComposeTrainAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeTrainAppTheme {
                val navController = rememberNavController()
                var screen: NavigationRoutes by remember { mutableStateOf(NavigationRoutes.Grid) }
                val scope = rememberCoroutineScope()
                val scaffoldState = rememberScaffoldState()

                Scaffold(
                    topBar = {
                        TrainTopBar(screen, navController, scaffoldState, scope)
                    },
                    scaffoldState = scaffoldState,
                    bottomBar = { CustomBottomNavigationBar(navController = navController) }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NavHost(
                            navController = navController,
                            startDestination = NavigationRoutes.Grid.route
                        ) {
                            composable(route = NavigationRoutes.ColumnRow.route) {
                                screen = NavigationRoutes.ColumnRow
                                ColumnRowScreen(
                                    scope = scope,
                                    scaffoldState = scaffoldState,
                                    navController = navController
                                )
                            }
                            composable(route = NavigationRoutes.Grid.route) {
                                screen = NavigationRoutes.Grid
                                GridScreen(
                                    scope = scope,
                                    scaffoldState = scaffoldState,
                                    navController = navController
                                )
                            }
                            composable(
                                route = "${NavigationRoutes.DetailCharacter.route}/{id}",
                                arguments = listOf(
                                    navArgument("id") {
                                        type = NavType.IntType
                                        nullable = false
                                    }
                                )
                            ) { backStackEntry ->
                                screen = NavigationRoutes.DetailCharacter
                                DetailScreen(
                                    backStackEntry.arguments?.getInt("id") ?: 0,
                                    scaffoldState,
                                    scope
                                )
                            }
                            composable(route = NavigationRoutes.Todo.route) {
                                screen = NavigationRoutes.Todo
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.Red)
                                ) {
                                }
                            }
                            composable(route = NavigationRoutes.Profile.route) {
                                screen = NavigationRoutes.Profile
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.Green)
                                ) {
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TrainTopBar(
    screen: NavigationRoutes,
    navController: NavController,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
) {
    var expanded by remember { mutableStateOf(false) }

    if (screen == NavigationRoutes.ColumnRow ||
        screen == NavigationRoutes.Grid ||
        screen == NavigationRoutes.DetailCharacter
    ) TopAppBar(
        title = {
            Text(text = screen.title ?: "")
        },
        navigationIcon = {
            if (screen == NavigationRoutes.DetailCharacter) {
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
                        if (screen != NavigationRoutes.Grid)
                            navController.navigate(NavigationRoutes.Grid.route)
                    }) {
                        Text(text = "Grid")
                    }
                    Divider()
                    DropdownMenuItem(onClick = {
                        expanded = false
                        if (screen != NavigationRoutes.ColumnRow)
                            navController.navigate(NavigationRoutes.ColumnRow.route)
                    }) {
                        Text(text = "Normal")
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
