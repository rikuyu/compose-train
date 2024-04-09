package com.example.feature.museum.coroutinetimer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import theme.M3TrainAppTheme

class CoroutineTimerActivity : ComponentActivity() {

    companion object {
        fun createIntent(context: Context) = Intent(context, CoroutineTimerActivity::class.java)
    }

    private val viewModel by viewModels<CoroutineTimerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            M3TrainAppTheme(darkTheme = false) {
                CoroutineTimerScreen(viewModel = viewModel)
            }
        }
    }
}
