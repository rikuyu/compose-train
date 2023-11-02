package com.example.feature.catalog.draggablebox

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import theme.M3TrainAppTheme

class DraggableBoxActivity : ComponentActivity() {

    companion object {
        fun createIntent(context: Context) = Intent(context, DraggableBoxActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            M3TrainAppTheme(darkTheme = false) {
                DraggableText()
            }
        }
    }
}
