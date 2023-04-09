package com.example.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.ui.home.detail.addDetail
import com.example.ui.home.favorite.addFavorite
import com.example.ui.home.grid.addGrid
import com.example.ui.mypage.addMyPage
import com.example.ui.todo.TodoViewModel
import com.example.ui.todo.todo.*
import com.example.ui.utils.Routes
import com.example.ui.utils.compose.CustomBottomNavigationBar
import com.example.ui.utils.compose.TrainFloatingActionButton
import com.example.ui.utils.compose.TrainTopBar
import com.example.ui.utils.theme.M3TrainAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val todoViewModel: TodoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val currentUser = todoViewModel.firebaseUser.value

        setContent {
            M3TrainAppTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

                val systemUiController = rememberSystemUiController()
                val useDarkIcons = !isSystemInDarkTheme()

                systemUiController.setSystemBarsColor(
                    color = Color.Transparent,
                    darkIcons = useDarkIcons,
                )

                Scaffold(
                    topBar = { TrainTopBar(navController, scrollBehavior) },
                    scaffoldState = scaffoldState,
                    bottomBar = { CustomBottomNavigationBar(navController, currentUser) },
                    floatingActionButton = { TrainFloatingActionButton(navController) },
                ) { innerPadding ->
                    Surface(
                        modifier = Modifier.padding(innerPadding),
                        color = MaterialTheme.colorScheme.background,
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = Routes.Grid.route,
                        ) {
                            addGrid(scaffoldState, navController)
                            addFavorite(scaffoldState, navController)
                            addDetail(scaffoldState)
                            addLogIn(navController)
                            addSignUp(navController)
                            addTodo(navController, currentUser)
                            addAddTodo(navController)
                            addUpdateTodo(navController)
                            addMyPage()
                        }
                    }
                }
            }
        }
        getAndroidDeviceId()
    }

    // Android端末固有のIDを取得する方法
    private fun getAndroidDeviceId() {
        @SuppressLint("HardwareIds")
        val androidId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        Log.d("ANDROID_ID", "androidId: $androidId")
    }
}