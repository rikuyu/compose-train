package com.example.feature.museum.snapshotFlow

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import theme.M3TrainAppTheme

class SnapshotFlowActivity : ComponentActivity() {

    companion object {
        fun createIntent(context: Context) = Intent(context, SnapshotFlowActivity::class.java)
    }

    private val viewModel: SnapshotFlowViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            M3TrainAppTheme(darkTheme = false) {
                // SnapshotFlowScreen1(viewModel = viewModel)
                SnapshotFlowScreen2()
            }
        }
    }
}
