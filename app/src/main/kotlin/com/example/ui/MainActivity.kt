package com.example.ui

import Routes
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
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.addAddTodo
import com.example.addMuseum
import com.example.addDetail
import com.example.addFavorite
import com.example.addGrid
import com.example.addLogIn
import com.example.addSignUp
import com.example.addTodo
import com.example.addUpdateTodo
import com.example.feature.museum.animtextchatgpt.AnimTextChatGPTActivity
import com.example.feature.museum.autoscroll.AutoScrollActivity
import com.example.feature.museum.bankcard.BankCardActivity
import com.example.feature.museum.canvas.CanvasActivity
import com.example.feature.museum.circularprogressbar.CircularProgressBarActivity
import com.example.feature.museum.draggablebox.DraggableBoxActivity
import com.example.feature.museum.remember.RememberActivity
import com.example.feature.museum.score.ScoreBoardActivity
import com.example.feature.museum.scrollspacer.ScrollSpacerActivity
import com.example.feature.museum.shakeicon.ShakeIconActivity
import com.example.feature.museum.share.ShareActivity
import com.example.feature.museum.snapshotFlow.SnapshotFlowActivity
import com.example.feature.museum.spotlight.SpotLightActivity
import com.example.feature.museum.threadscard.ThreadsCardActivity
import com.example.todo.TodoViewModel
import com.example.ui.utils.compose.CustomBottomNavigationBar
import com.example.ui.utils.compose.TrainFloatingActionButton
import com.example.ui.utils.compose.TrainTopBar
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import theme.M3TrainAppTheme

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

                systemUiController.apply {
                    setStatusBarColor(
                        color = if (useDarkIcons) Color.White else Color.Black,
                        darkIcons = useDarkIcons,
                    )
                    setNavigationBarColor(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                        darkIcons = useDarkIcons,
                    )
                }

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
                            addMuseum(
                                openScoreBoard = { startActivity(ScoreBoardActivity.createIntent(this@MainActivity)) },
                                openDraggableBox = { startActivity(DraggableBoxActivity.createIntent(this@MainActivity)) },
                                openThreadsCard = { startActivity(ThreadsCardActivity.createIntent(this@MainActivity)) },
                                openCanvas = { startActivity(CanvasActivity.createIntent(this@MainActivity)) },
                                openSpotLight = { startActivity(SpotLightActivity.createIntent(this@MainActivity)) },
                                openShare = { startActivity(ShareActivity.createIntent(this@MainActivity)) },
                                openAutoScroll = { startActivity(AutoScrollActivity.createIntent(this@MainActivity)) },
                                openCircularProgressBar = { startActivity(CircularProgressBarActivity.createIntent(this@MainActivity)) },
                                openBankCard = { startActivity(BankCardActivity.createIntent(this@MainActivity)) },
                                openShakeIcon = { startActivity(ShakeIconActivity.createIntent(this@MainActivity)) },
                                openRemember = { startActivity(RememberActivity.createIntent(this@MainActivity)) },
                                openSnapshotFlow = { startActivity(SnapshotFlowActivity.createIntent(this@MainActivity)) },
                                openAnimTextChatGPT = { startActivity(AnimTextChatGPTActivity.createIntent(this@MainActivity)) },
                                openScrollSpacerActivity = { startActivity(ScrollSpacerActivity.createIntent(this@MainActivity)) },
                            )
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
