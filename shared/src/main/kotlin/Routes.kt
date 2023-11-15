import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Add
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Routes(val route: String) {

   data object Grid : Routes("grid")

   data object Favorite : Routes("favorite")

   data object Museum : Routes("museum")

   data object DetailCharacter : Routes("detail") {
        fun createRoute(id: Int) = "${this.route}/$id"
    }

   data object LogIn : Routes("login")

   data object SignUp : Routes("signup")

   data object Todo : Routes("todo")

   data object CreateTodo : Routes("create")

   data object UpdateTodo : Routes("update") {
        fun createRoute(id: String) = "${this.route}/$id"
    }
}

enum class BottomNavigationItem(val label: String, val icon: ImageVector) {
    GRID("Grid", Icons.Filled.Home),
    TODO("Todo", Icons.Outlined.Add),
    MUSEUM("Museum", Icons.Filled.Search),
}
