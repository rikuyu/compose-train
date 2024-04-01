package com.example.feature.museum.pip

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import theme.M3TrainAppTheme

class PictureInPictureActivity : ComponentActivity() {

    companion object {
        fun createIntent(context: Context) = Intent(context, PictureInPictureActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Lifecycle", "fun onCreate")
        setContent {
            M3TrainAppTheme(darkTheme = false) {
                val isPip = rememberIsInPipMode()

                // PipListenerPreApi12()

                if (isPip) {
                    PipScreen()
                } else {
                    NotPipScreen()
                }
                val observer = remember {
                    LifecycleEventObserver { _, event ->
                        when (event) {
                            Lifecycle.Event.ON_CREATE -> Log.d("Lifecycle", "onCreate")
                            Lifecycle.Event.ON_START -> Log.d("Lifecycle", "onStart")
                            Lifecycle.Event.ON_RESUME -> Log.d("Lifecycle", "onResume")
                            Lifecycle.Event.ON_PAUSE -> Log.d("Lifecycle", "onPause")
                            Lifecycle.Event.ON_STOP -> Log.d("Lifecycle", "onStop")
                            Lifecycle.Event.ON_DESTROY -> Log.d("Lifecycle", "onDestroy")
                            Lifecycle.Event.ON_ANY -> Log.d("Lifecycle", "onAny")
                        }
                    }
                }
                val lifecycle = LocalLifecycleOwner.current.lifecycle
                DisposableEffect(Unit) {
                    lifecycle.addObserver(observer)
                    onDispose { lifecycle.removeObserver(observer) }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("Lifecycle", "fun onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Lifecycle", "fun onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Lifecycle", "fun onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Lifecycle", "fun onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Lifecycle", "fun onDestroy")
    }
}
