package com.example.ui.todo.todo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.composetrainapp.R
import com.example.data.utils.Result
import com.example.model.User
import com.example.ui.todo.EmailForm
import com.example.ui.todo.ErrorMessage
import com.example.ui.todo.InputState
import com.example.ui.todo.NameForm
import com.example.ui.todo.PasswordForm
import com.example.ui.todo.TodoViewModel
import com.example.ui.utils.compose.FullScreenLoadingIndicator
import com.example.ui.utils.Routes
import com.example.ui.utils.showToast

fun NavGraphBuilder.addSignUp(navController: NavHostController) {
    composable(route = Routes.SignUp.route) {
        TodoSignUpScreen(navController)
    }
}

@Composable
fun TodoSignUpScreen(
    navController: NavHostController,
    viewModel: TodoViewModel = hiltViewModel(),
) {
    val user by viewModel.user.collectAsStateWithLifecycle()

    when (user) {
        is Result.LoadingState -> {
            if ((user as Result.LoadingState).name == Result.LoadingState.NotLoading.name) {
                TodoSignUpContent()
            } else {
                FullScreenLoadingIndicator()
            }
        }
        is Result.Error -> {
            LocalContext.current.showToast("Error")
            TodoSignUpContent()
        }
        is Result.Success -> {
            if ((user as Result.Success<User?>).data == null) {
                LocalContext.current.showToast("Error")
                TodoSignUpContent()
            } else {
                if (viewModel.isFirstLogIn) {
                    LocalContext.current.showToast("ようこそ ${(user as Result.Success<User?>).data?.name}")
                    viewModel.setIsFirstLogIn()
                }
                TodoScreen(navController, null)
            }
        }
    }
}

@Composable
fun TodoSignUpContent(viewModel: TodoViewModel = hiltViewModel()) {
    val signUpValueState by viewModel.signUpValueState
    val focusManager = LocalFocusManager.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Image(
            painter = painterResource(id = R.drawable.image_signup),
            contentDescription = null,
            modifier = Modifier.fillMaxHeight(0.2F),
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "Sign Up",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    // letterSpacing = TextUnit.Companion.Sp(2)
                ),
                fontSize = 26.sp,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                NameForm(
                    name = signUpValueState.name,
                    isError = signUpValueState.nameValid == InputState.NotValid,
                    onNext = { focusManager.moveFocus(FocusDirection.Down) },
                    onNameChange = { viewModel.updateSignUpName(it) },
                )
                ErrorMessage(text = "名前は2文字～5文字", flag = signUpValueState.nameValid == InputState.NotValid)
                EmailForm(
                    email = signUpValueState.email,
                    isError = signUpValueState.emailValid == InputState.NotValid,
                    onNext = { focusManager.moveFocus(FocusDirection.Down) },
                    onEmailChange = { viewModel.updateSignUpEmail(it) },
                )
                ErrorMessage(text = "正しいメールアドレスではありません", flag = signUpValueState.emailValid == InputState.NotValid)
                PasswordForm(
                    password = signUpValueState.password,
                    isError = signUpValueState.passwordValid == InputState.NotValid,
                    visibility = signUpValueState.passwordVisibility,
                    toggleVisibility = { viewModel.toggleSignUpPasswordVisibility() },
                    onNext = { focusManager.moveFocus(FocusDirection.Down) },
                    onDone = null,
                    onPasswordChange = { viewModel.updateSignUpPassword(it) },
                )
                ErrorMessage(
                    text = "パスワードは半角数字英小文字で6～10文字",
                    flag = signUpValueState.passwordValid == InputState.NotValid,
                )
                PasswordForm(
                    label = "Confirmation Password",
                    password = signUpValueState.confirmationPassword,
                    isError = signUpValueState.confirmationPasswordValid == InputState.NotValid,
                    visibility = signUpValueState.confirmationPasswordVisibility,
                    toggleVisibility = { viewModel.toggleSignUpConfirmationPasswordVisibility() },
                    onNext = null,
                    onDone = { focusManager.clearFocus() },
                    onPasswordChange = { viewModel.updateSignUpConfirmationPassword(it) },
                )
                ErrorMessage(
                    text = "パスワードが等しくありません",
                    flag = signUpValueState.confirmationPasswordValid == InputState.NotValid,
                )
                Spacer(modifier = Modifier.padding(10.dp))
                Button(
                    enabled = signUpValueState.canRequestSignUp,
                    onClick = { viewModel.signUp() },
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(50.dp),
                ) {
                    Text(text = "Sign Up", fontSize = 20.sp)
                }
            }
        }
    }
}