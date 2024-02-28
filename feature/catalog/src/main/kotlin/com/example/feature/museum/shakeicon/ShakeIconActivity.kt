package com.example.feature.museum.shakeicon

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import theme.M3TrainAppTheme

class ShakeIconActivity : ComponentActivity() {

    companion object {
        fun createIntent(context: Context) = Intent(context, ShakeIconActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            M3TrainAppTheme(darkTheme = false) {
                ShakeIcon()
            }
        }
    }
}
