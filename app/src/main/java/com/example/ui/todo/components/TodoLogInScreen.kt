package com.example.ui.todo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.composetrainapp.R
import com.example.data.utils.Result
import com.example.model.User
import com.example.ui.todo.components.TodoScreen
import com.example.ui.utils.*

fun NavGraphBuilder.addLogIn(
    navController: NavHostController,
    changeTodoScreen: () -> Unit,
    changeSignUpScreen: () -> Unit,
    changeLogInScreen: () -> Unit,
) {
    composable(route = Routes.LogIn.route) {
        changeLogInScreen()
        TodoLogInScreen(navController, changeTodoScreen, changeSignUpScreen)
    }
}

@Composable
fun TodoLogInScreen(
    navController: NavHostController,
    changeTodoScreen: () -> Unit,
    changeSignUpScreen: () -> Unit,
    viewModel: TodoViewModel = hiltViewModel()
) {
    val user by viewModel.user.collectAsStateWithLifecycle()

    when (user) {
        is Result.LoadingState -> {
            if ((user as Result.LoadingState).name == Result.LoadingState.NotLoading.name) {
                TodoLogInContent(navController, viewModel)
            } else {
                LoadingScreen()
            }
        }
        is Result.Error -> {
            LocalContext.current.showToast("ユーザーが見つかりません\n新規登録してください")
            TodoSignUpContent(changeSignUpScreen)
        }
        is Result.Success -> {
            if ((user as Result.Success<User?>).data == null) {
                LocalContext.current.showToast("ユーザーが見つかりません\n新規登録してください")
                TodoSignUpContent(changeSignUpScreen)
            } else {
                if (viewModel.isFirstLogIn) {
                    LocalContext.current.showToast("ようこそ ${(user as Result.Success<User?>).data?.name}")
                    viewModel.setIsFirstLogIn()
                }
                TodoScreen(navController, null, changeTodoScreen)
            }
        }
    }
}

@Composable
fun TodoLogInContent(
    navController: NavController,
    viewModel: TodoViewModel
) {
    val logInValueState by viewModel.logInValueState
    val focusManager = LocalFocusManager.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        Image(
            painter = painterResource(id = R.drawable.image_login),
            contentDescription = null,
            modifier = Modifier.fillMaxHeight(0.2F)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Log In",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    // letterSpacing = TextUnit.Companion.Sp(2)
                ),
                fontSize = 26.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                EmailForm(
                    email = logInValueState.email,
                    isError = logInValueState.emailValid == InputState.NotValid,
                    onNext = { focusManager.moveFocus(FocusDirection.Down) },
                    onEmailChange = { viewModel.updateLoginEmail(it) },
                )
                ErrorMessage(
                    text = "正しいメールアドレスではありません",
                    flag = logInValueState.emailValid == InputState.NotValid
                )
                PasswordForm(
                    password = logInValueState.password,
                    isError = logInValueState.passwordValid == InputState.NotValid,
                    visibility = logInValueState.passwordVisibility,
                    toggleVisibility = { viewModel.toggleLoginPasswordVisibility() },
                    onNext = null,
                    onDone = { focusManager.clearFocus() },
                    onPasswordChange = { viewModel.updateLoginPassword(it) },
                )
                ErrorMessage(
                    text = "パスワードは半角数字英小文字で6～10文字",
                    flag = logInValueState.passwordValid == InputState.NotValid
                )
                Spacer(modifier = Modifier.padding(10.dp))
                Button(
                    enabled = logInValueState.canRequestLogIn,
                    onClick = { viewModel.logIn() },
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(50.dp)
                ) {
                    Text(text = "Log In", fontSize = 20.sp)
                }
                Spacer(modifier = Modifier.padding(4.dp))
                TextButton(onClick = { navController.navigate(Routes.SignUp.route) }) {
                    Text(text = "Sign Up", fontSize = 20.sp, color = MaterialTheme.colors.primary)
                }
            }
        }
    }
}