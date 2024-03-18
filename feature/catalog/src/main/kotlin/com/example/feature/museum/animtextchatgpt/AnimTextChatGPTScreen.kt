package com.example.feature.museum.animtextchatgpt

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay


@Composable
fun AnimTextChatGPTScreen(
    text: String = "Hello! My name is GPT-3. I am a language model AI. How can I help you today?",
) {
    var animText by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        text.indices.forEach { index ->
            animText = text.substring(0, index + 1) + if (index + 1 == text.length) "" else "●"
            delay(50)
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Text(
            text = animText,
            color = Color.Black,
            fontSize = 16.sp,
        )
    }
}
