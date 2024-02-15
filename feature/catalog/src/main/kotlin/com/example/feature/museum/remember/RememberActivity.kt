package com.example.feature.museum.remember

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import theme.M3TrainAppTheme

class RememberActivity : ComponentActivity() {

    companion object {
        fun createIntent(context: Context) = Intent(context, RememberActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            M3TrainAppTheme(darkTheme = false) {
                RememberScreen(modifier = Modifier.fillMaxSize())
            }
        }
    }
}
