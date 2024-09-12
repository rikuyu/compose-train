package com.example.feature.museum.draggablebox

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import theme.M3TrainAppTheme

class DraggableBoxActivity : ComponentActivity() {

    companion object {
        fun createIntent(context: Context) = Intent(context, DraggableBoxActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
        setContent {
            M3TrainAppTheme(darkTheme = false) {
                DraggableBox()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
    }
}
