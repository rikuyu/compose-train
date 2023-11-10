package com.example.feature.museum

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import debounceClickable

fun LazyListScope.museumItem(
    label: String,
    onClick: () -> Unit,
) {
    item {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(MaterialTheme.colorScheme.background)
                .debounceClickable { onClick() },
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 20.dp),
                text = label,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
        Divider(color = MaterialTheme.colorScheme.onBackground)
    }
}
