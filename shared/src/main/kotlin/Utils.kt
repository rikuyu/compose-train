import android.content.Context
import android.widget.Toast
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT)
        .show()
}

suspend fun showSnackBarWithArg(
    scaffoldState: ScaffoldState,
    message: String,
    actionLabel: String,
    arg: Int,
    action: ((Int) -> Unit),
) {
    when (
        scaffoldState.snackbarHostState.showSnackbar(
            message = message,
            actionLabel = actionLabel,
        )
    ) {
        SnackbarResult.ActionPerformed -> action.invoke(arg)
        SnackbarResult.Dismissed -> {}
    }
}
