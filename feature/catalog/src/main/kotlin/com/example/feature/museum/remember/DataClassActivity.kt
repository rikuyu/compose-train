package com.example.feature.museum.remember

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import theme.M3TrainAppTheme

class DataClassActivity : ComponentActivity() {

    companion object {
        fun createIntent(context: Context) = Intent(context, DataClassActivity::class.java)
    }

    private val viewModel by viewModels<DataClassViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            M3TrainAppTheme(darkTheme = false) {
                DataClassScreen(
                    viewModel = viewModel,
                )
            }
        }
    }
}
