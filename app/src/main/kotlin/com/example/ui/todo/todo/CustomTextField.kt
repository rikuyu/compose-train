package com.example.ui.todo.todo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    label: String,
    errorText: String,
    value: String,
    changeValue: (String) -> Unit,
    checkIsValid: (String) -> Boolean,
) {
    var isEmailValid by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            value = value,
            onValueChange = {
                isEmailValid = !checkIsValid(it)
                changeValue(it)
            },
            label = {
                Text(
                    text = label,
                    style = MaterialTheme.typography.body2,
                )
            },
            singleLine = true,
            modifier = modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.body1,
            isError = isEmailValid,
        )
        if (isEmailValid) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = errorText,
                    modifier = Modifier.fillMaxWidth(),
                    style = LocalTextStyle.current.copy(color = MaterialTheme.colors.error),
                )
            }
        }
    }
}