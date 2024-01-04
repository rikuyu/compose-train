package com.example.feature.museum.bankcard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import theme.M3TrainAppTheme

class BankCardActivity : ComponentActivity() {

    companion object {
        fun createIntent(context: Context) = Intent(context, BankCardActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            M3TrainAppTheme(darkTheme = false) {
                Box(modifier = Modifier.fillMaxSize()) {
                    BankCardScreen(
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .align(Alignment.Center),
                    )
                }
            }
        }
    }
}

