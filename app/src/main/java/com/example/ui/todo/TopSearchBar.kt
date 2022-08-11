package com.example.ui.todo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.ui.utils.checkIsEmailValid

@Composable
fun TopSearchBar(
    modifier: Modifier = Modifier,
    query: String,
    changeQuery: (String) -> Unit,
) {
    var isEmailValid by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = query,
        onValueChange = {
            isEmailValid = !checkIsEmailValid(it)
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
        textStyle = MaterialTheme.typography.body1,
        isError = isEmailValid,
    )
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