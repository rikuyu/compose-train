package com.example.ui.todo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.composetrainapp.R
import com.example.model.Todo
import com.example.ui.utils.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListItem(
    todo: Todo,
    navController: NavController,
    delete: (String) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    painter = painterResource(id = R.drawable.ic_refresh),
                    contentDescription = null,
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "unknown",
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        text = todo.createdAt,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
                if (todo.isImportant)
                    Icon(
                        modifier = Modifier.size(30.dp),
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.Red
                    )
                Spacer(modifier = Modifier.width(10.dp))
                IconButton(
                    onClick = { navController.navigate(Routes.UpdateTodo.createRoute(todo.id)) },
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        tint = Color(0xFF23DC73)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = { delete(todo.id) },
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = Color(0xFFFF8095)
                    )
                }
            }
            Text(
                text = todo.title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 12.dp, bottom = 8.dp),
            )
            Text(
                text = todo.body,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}