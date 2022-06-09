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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composetrainapp.ui.home.HomeScreen
import com.example.composetrainapp.ui.utils.theme.ComposeTrainAppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeTrainAppTheme {
                val navController = rememberNavController()
                var screen: NavigationRoutes by remember { mutableStateOf(NavigationRoutes.Home) }
                var expanded by remember { mutableStateOf(false) }
                Scaffold(
                    topBar = {
                        if (screen == NavigationRoutes.Home) TopAppBar(
                            title = {
                                Text(text = "Home")
                            },
                            actions = {
                                IconButton(onClick = { expanded = true }) {
                                    Icon(Icons.Filled.MoreVert, contentDescription = null)
                                }
                                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                                    DropdownMenuItem(onClick = { expanded = false }) {
                                        Text(text = "Row Column")
                                    }
                                    Divider()
                                    DropdownMenuItem(onClick = { expanded = false }) {
                                        Text(text = "Grid")
                                    }
                                }
                            }
                        )
                    },
                    bottomBar = { CustomBottomNavigationBar(navController = navController) }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NavHost(
                            navController = navController,
                            startDestination = NavigationRoutes.Home.route
                        ) {
                            composable(route = NavigationRoutes.Home.route) {
                                screen = NavigationRoutes.Home
                                HomeScreen()
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
