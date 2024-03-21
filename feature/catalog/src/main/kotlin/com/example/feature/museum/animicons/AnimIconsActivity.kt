package com.example.feature.museum.animicons

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import theme.M3TrainAppTheme

class AnimIconsActivity : ComponentActivity() {

    companion object {
        fun createIntent(context: Context) = Intent(context, AnimIconsActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            M3TrainAppTheme(darkTheme = false) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    ShakeIcon()
                    VolumeBars()
                }
            }
        }
    }
}
