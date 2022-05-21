package com.example.composetrainapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composetrainapp.ui.home.HomeScreen
import com.example.composetrainapp.ui.utils.collectAsStateWithLifecycle
import com.example.composetrainapp.ui.utils.theme.ComposeTrainAppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeTrainAppTheme {

                val navController = rememberNavController()

                Scaffold(
                    bottomBar = { CustomBottomNavigationBar(navController = navController) }
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = NavigationRoutes.Home.route
                    ) {
                        composable(route = NavigationRoutes.Home.route) {
                            HomeScreen()
                        }
                        composable(route = NavigationRoutes.Todo.route) {
                            Column(modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Red)) {

                            }
                        }
                        composable(route = NavigationRoutes.Profile.route) {
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
