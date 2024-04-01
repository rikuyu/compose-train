import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb

@Composable
fun ComponentActivity.EdgeToEdge(isDarkMode: Boolean) {
    val systemIconColor = MaterialTheme.colorScheme.primary.toArgb()
    enableEdgeToEdge(
        statusBarStyle = if (isDarkMode) {
            SystemBarStyle.dark(systemIconColor)
        } else {
            SystemBarStyle.light(systemIconColor, systemIconColor)
        },
        navigationBarStyle = if (isDarkMode) {
            SystemBarStyle.dark(systemIconColor)
        } else {
            SystemBarStyle.light(systemIconColor, systemIconColor)
        },
    )
}
