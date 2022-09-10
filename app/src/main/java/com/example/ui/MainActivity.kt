package com.example.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ui.home.column_row.addColumnRow
import com.example.ui.home.detail.addDetail
import com.example.ui.home.favorite.addFavorite
import com.example.ui.home.grid.addGrid
import com.example.ui.todo.addAddTodo
import com.example.ui.todo.addTodo
import com.example.ui.utils.CustomBottomNavigationBar
import com.example.ui.utils.Routes
import com.example.ui.utils.showSnackBar
import com.example.ui.utils.theme.ComposeTrainAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val screenViewModel: ScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeTrainAppTheme {
                val navController = rememberNavController()
                val screen: Routes by screenViewModel.screen
                val scope = rememberCoroutineScope()
                val scaffoldState = rememberScaffoldState()

                Scaffold(
                    topBar = { TrainTopBar(screen, navController, scaffoldState, scope) },
                    scaffoldState = scaffoldState,
                    bottomBar = { if (screen != Routes.AddTodo) CustomBottomNavigationBar(navController, screen) },
                    floatingActionButton = { TrainFloatingActionButton(screen, navController) }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NavHost(
                            navController = navController,
                            startDestination = Routes.Grid.route
                        ) {
                            addColumnRow(scope, scaffoldState, navController) {
                                screenViewModel.setScreen(Routes.ColumnRow)
                            }
                            addGrid(scope, scaffoldState, navController) { screenViewModel.setScreen(Routes.Grid) }
                            addFavorite(scope, scaffoldState, navController) {
                                screenViewModel.setScreen(Routes.Favorite)
                            }
                            addDetail(scope, scaffoldState) { screenViewModel.setScreen(Routes.DetailCharacter) }
                            addTodo(navController) { screenViewModel.setScreen(Routes.Todo) }
                            addAddTodo(navController) { screenViewModel.setScreen(Routes.AddTodo) }
                            composable(route = Routes.Profile.route) {
                                screenViewModel.setScreen(Routes.Profile)
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
    screen: Routes,
    navController: NavController,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
) {
    var expanded by remember { mutableStateOf(false) }

    if (screen == Routes.ColumnRow ||
        screen == Routes.Grid ||
        screen == Routes.DetailCharacter ||
        screen == Routes.Favorite ||
        screen == Routes.Todo ||
        screen == Routes.AddTodo
    ) TopAppBar(
        title = { Text(text = screen.title ?: "") },
        navigationIcon = {
            if (screen == Routes.DetailCharacter || screen == Routes.AddTodo) {
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
                        if (screen != Routes.ColumnRow)
                            navController.navigate(Routes.ColumnRow.route)
                    }) {
                        Text(text = "Normal")
                    }
                    Divider()
                    DropdownMenuItem(onClick = {
                        expanded = false
                        if (screen != Routes.Favorite)
                            navController.navigate(Routes.Favorite.route)
                    }) {
                        Text(text = "Favorite")
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

@Composable
fun TrainFloatingActionButton(screen: Routes, navController: NavController) {
    if (screen == Routes.Todo)
        FloatingActionButton(
            onClick = { navController.navigate(Routes.AddTodo.route) },
            backgroundColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(Icons.Filled.Add, contentDescription = null, tint = MaterialTheme.colorScheme.surface)
        }
}