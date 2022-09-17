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
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ui.home.column_row.addColumnRow
import com.example.ui.home.detail.addDetail
import com.example.ui.home.favorite.addFavorite
import com.example.ui.home.grid.addGrid
import com.example.ui.todo.*
import com.example.ui.utils.*
import com.example.ui.utils.theme.ComposeTrainAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val screenViewModel: ScreenViewModel by viewModels()
    private val todoViewModel: TodoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val currentUser = todoViewModel.firebaseUser.value

        setContent {
            ComposeTrainAppTheme {
                val navController = rememberNavController()
                val screen: Routes by screenViewModel.screen
                val scope = rememberCoroutineScope()
                val scaffoldState = rememberScaffoldState()

                Scaffold(
                    topBar = { TrainTopBar(screen, navController, scaffoldState, scope) },
                    scaffoldState = scaffoldState,
                    bottomBar = {
                        if (screen != Routes.AddTodo || screen != Routes.UpdateTodo) CustomBottomNavigationBar(
                            navController,
                            screen,
                            currentUser
                        )
                    },
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
                            addLogIn(
                                navController,
                                { screenViewModel.setScreen(Routes.Todo) },
                                { screenViewModel.setScreen(Routes.SignUp) }
                            ) { screenViewModel.setScreen(Routes.LogIn) }
                            addSignUp(
                                navController,
                                { screenViewModel.setScreen(Routes.Todo) }
                            ) { screenViewModel.setScreen(Routes.SignUp) }
                            addTodo(navController) { screenViewModel.setScreen(Routes.Todo) }
                            addAddTodo(navController) { screenViewModel.setScreen(Routes.AddTodo) }
                            addUpdateTodo(navController) { screenViewModel.setScreen(Routes.UpdateTodo) }
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