package com.example.ui.todo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ui.utils.checkIsEmailValid

@Composable
fun TopSearchBar(modifier: Modifier = Modifier) {

    var text by remember { mutableStateOf("") }
    var isEmailValid by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            isEmailValid = !checkIsEmailValid(text)
        },
        label = {
            Text(
                text = "search todo",
                style = MaterialTheme.typography.body2
            )
        },
        singleLine = true,
        modifier = modifier.fillMaxWidth(),
        textStyle = MaterialTheme.typography.h5,
        isError = isEmailValid,
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = text,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.h5
        )
    }
    if (isEmailValid) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "無効なメールアドレス形式",
                modifier = Modifier.fillMaxWidth(),
                style = LocalTextStyle.current.copy(color = MaterialTheme.colors.error)
            )
        }
    }
}