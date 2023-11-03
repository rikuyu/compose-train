package com.example.ui.utils.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import BottomNavigationItem
import Routes
import com.google.firebase.auth.FirebaseUser

private val String?.isTop
    get() = this == Routes.Grid.route ||
        this == Routes.Todo.route ||
        this == Routes.Catalog.route ||
        this == Routes.LogIn.route ||
        this == Routes.SignUp.route

@Composable
fun CustomBottomNavigationBar(navController: NavController, currentUser: FirebaseUser?) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val isShowBottomBar = currentDestination?.hierarchy?.any { it.route.isTop } ?: false

    AnimatedVisibility(
        visible = isShowBottomBar,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BottomNavigationItem.values().forEachIndexed { _, item ->
                CustomBottomNavigationItem(
                    bottomNavigationItem = item,
                    isSelected = currentDestination?.hierarchy?.any {
                        it.route == item.label.lowercase()
                    } == true,
                ) {
                    if (item == BottomNavigationItem.TODO && currentUser == null) {
                        navController.navigate(Routes.LogIn.route)
                    } else {
                        navController.navigate(item.label.lowercase())
                    }
                }
            }
        }
    }
}

@Composable
fun CustomBottomNavigationItem(
    bottomNavigationItem: BottomNavigationItem,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val backgroundColor =
        if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else Color.Transparent
    val contentColor =
        if (isSelected) {
            MaterialTheme.colorScheme.onPrimary
        } else {
            MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
        }

    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable(onClick = onClick),
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Icon(
                imageVector = bottomNavigationItem.icon,
                contentDescription = null,
                tint = contentColor,
            )
            AnimatedVisibility(visible = isSelected) {
                Text(
                    text = bottomNavigationItem.label,
                    color = contentColor,
                )
            }
        }
    }
}
