package com.example.composetrainapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composetrainapp.ui.home.ColumnRowView
import com.example.composetrainapp.ui.home.GridView
import com.example.composetrainapp.ui.utils.CustomBottomNavigationBar
import com.example.composetrainapp.ui.utils.theme.ComposeTrainAppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeTrainAppTheme {
                val navController = rememberNavController()
                var screen: NavigationRoutes by remember { mutableStateOf(NavigationRoutes.Grid) }
                val listState = rememberLazyListState()
                val scope = rememberCoroutineScope()
                val scaffoldState = rememberScaffoldState()

                Scaffold(
                    topBar = {
                        TrainTopBar(screen, navController)
                    },
                    bottomBar = { CustomBottomNavigationBar(navController = navController) }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NavHost(
                            navController = navController,
                            startDestination = NavigationRoutes.Grid.route
                        ) {
                            composable(route = NavigationRoutes.ColumnRow.route) {
                                screen = NavigationRoutes.ColumnRow
                                ColumnRowView(
                                    scope = scope,
                                    scaffoldState = scaffoldState,
                                    listState = listState
                                )
                            }
                            composable(route = NavigationRoutes.Grid.route) {
                                screen = NavigationRoutes.Grid
                                GridView(
                                    scope = scope,
                                    scaffoldState = scaffoldState,
                                )
                            }
                            composable(route = NavigationRoutes.Todo.route) {
                                screen = NavigationRoutes.Todo
                                Column(modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Red)) {

                                }
                            }
                            composable(route = NavigationRoutes.Profile.route) {
                                screen = NavigationRoutes.Profile
                                Column(modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Green)) {

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
) {
    var expanded by remember { mutableStateOf(false) }

    if (screen == NavigationRoutes.ColumnRow || screen == NavigationRoutes.Grid) TopAppBar(
        title = {
            Text(text = "Home")
        },
        actions = {
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Filled.MoreVert, contentDescription = null)
            }
            DropdownMenu(expanded = expanded,
                onDismissRequest = { expanded = false }) {
                DropdownMenuItem(onClick = {
                    expanded = false
                    if (screen != NavigationRoutes.ColumnRow)
                        navController.navigate(NavigationRoutes.ColumnRow.route)
                }) {
                    Text(text = "Row Column")
                }
                Divider()
                DropdownMenuItem(onClick = {
                    expanded = false
                    if (screen != NavigationRoutes.Grid)
                        navController.navigate(NavigationRoutes.Grid.route)
                }) {
                    Text(text = "Grid")
                }
            }
        }
    )
}