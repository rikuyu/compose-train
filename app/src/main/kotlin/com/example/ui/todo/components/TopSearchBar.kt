package com.example.ui.todo.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TopSearchBar(
    modifier: Modifier = Modifier,
    query: String,
    changeQuery: (String) -> Unit,
) {
    OutlinedTextField(
        value = query,
        onValueChange = {
            changeQuery(it)
        },
        label = {
            Text(
                text = "search todo",
                style = MaterialTheme.typography.body2
            )
        },
        singleLine = true,
        modifier = modifier.fillMaxWidth(),
        textStyle = MaterialTheme.typography.body1
    )
}