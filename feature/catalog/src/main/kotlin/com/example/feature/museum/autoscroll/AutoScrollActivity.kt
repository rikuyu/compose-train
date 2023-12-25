package com.example.feature.museum.autoscroll

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import theme.M3TrainAppTheme

class AutoScrollActivity : ComponentActivity() {

    companion object {
        fun createIntent(context: Context): Intent = Intent(context, AutoScrollActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            M3TrainAppTheme {
                AutoScrollScreen()
            }
        }
    }
}
