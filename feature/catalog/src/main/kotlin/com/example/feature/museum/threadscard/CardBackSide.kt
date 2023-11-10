package com.example.feature.museum.threadscard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.feature.museum.R

@Composable
fun CardBackSide() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center,
    ) {
        CardBlackHalfCircle(modifier = Modifier.align(alignment = Alignment.TopCenter))
        Image(
            modifier = Modifier.size(size = 160.dp),
            painter = painterResource(id = R.drawable.ic_threads_icon),
            contentDescription =""
        )
        CardBlackHalfCircle(modifier = Modifier.align(alignment = Alignment.BottomCenter))
    }
}
