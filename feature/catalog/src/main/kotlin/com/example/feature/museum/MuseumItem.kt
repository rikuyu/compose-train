package com.example.feature.museum

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
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
        HorizontalDivider(color = MaterialTheme.colorScheme.onBackground)
    }
}

fun LazyListScope.museumItem(
    label1: String,
    onClick1: () -> Unit,
    label2: String,
    onClick2: () -> Unit,
) {
    item {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = label1,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .weight(1f)
                    .clickable { onClick1() },
            )
            Text(
                text = label2,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .weight(1f)
                    .clickable { onClick2() },
            )
        }
        HorizontalDivider(color = MaterialTheme.colorScheme.onBackground)
    }
}
