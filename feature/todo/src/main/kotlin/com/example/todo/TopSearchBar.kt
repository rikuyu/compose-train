package com.example.todo

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
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
                style = MaterialTheme.typography.bodyMedium,
            )
        },
        singleLine = true,
        modifier = modifier.fillMaxWidth(),
        textStyle = MaterialTheme.typography.bodyLarge,
    )
}
