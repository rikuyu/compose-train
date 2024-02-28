package com.example.feature.museum.remember

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RememberScreen(
    modifier: Modifier,
) {
    var text by remember { mutableIntStateOf(10) }
    val random1 by remember(text) { mutableIntStateOf((0..100).random()) }
    val random2 by remember { mutableIntStateOf((0..100).random()) }

    Column(
        modifier = modifier.padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "text = $text",
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "random1 (key有) = $random1",
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "random2 (key無) = $random2",
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = { text = (101..200).random() },
        ) {
            Text(
                text = "text update",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )
        }
    }
}
