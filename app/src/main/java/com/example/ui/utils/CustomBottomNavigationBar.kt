package com.example.ui.utils

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun CustomBottomNavigationBar(navController: NavController, screen: Routes) {
    val item = when (screen) {
        Routes.ColumnRow, Routes.Grid -> BottomNavigationItem.COLUMN_ROW
        Routes.Todo -> BottomNavigationItem.TODO
        else -> BottomNavigationItem.PROFILE
    }
    var selectedItem by remember { mutableStateOf(item) }
    Row(
        modifier = Modifier
            .background(MaterialTheme.colors.primary.copy(alpha = 0.3f))
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomNavigationItem.values().forEachIndexed { _, item ->
            CustomBottomNavigationItem(
                bottomNavigationItem = item,
                isSelected = selectedItem == item
            ) {
                selectedItem = item
                navController.navigate(item.label.lowercase())
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CustomBottomNavigationItem(
    bottomNavigationItem: BottomNavigationItem,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val backgroundColor =
        if (isSelected) MaterialTheme.colors.primary.copy(alpha = 0.1f) else Color.Transparent
    val contentColor =
        if (isSelected)
            MaterialTheme.colors.background
        else
            MaterialTheme.colors.primary.copy(alpha = 0.8f)

    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = bottomNavigationItem.icon,
                contentDescription = null,
                tint = contentColor
            )
            AnimatedVisibility(visible = isSelected) {
                Text(
                    text = bottomNavigationItem.label,
                    color = contentColor
                )
            }
        }
    }
}