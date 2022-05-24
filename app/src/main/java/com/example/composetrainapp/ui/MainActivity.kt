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
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composetrainapp.R
import com.example.composetrainapp.ui.home.HomeScreen
import com.example.composetrainapp.ui.utils.theme.ComposeTrainAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeTrainAppTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = { CustomBottomNavigationBar(navController = navController) }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
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
}
