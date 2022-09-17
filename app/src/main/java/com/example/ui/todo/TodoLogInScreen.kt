package com.example.ui.todo

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material.OutlinedTextField
import com.example.ui.utils.collectAsStateWithLifecycle
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.composetrainapp.R
import com.example.ui.utils.Routes
import com.example.data.utils.Result
import com.example.ui.utils.ErrorScreen
import com.example.ui.utils.LoadingScreen

fun NavGraphBuilder.addLogIn(
    navController: NavHostController,
    changeScreen: () -> Unit,
) {
    composable(route = Routes.LogIn.route) {
        changeScreen()
        TodoLogInScreen(navController)
    }
}

@Composable
fun TodoLogInScreen(
    navController: NavController,
    viewModel: TodoViewModel = hiltViewModel()
) {
    val user by viewModel.user.collectAsStateWithLifecycle()
    val logInValueState by viewModel.logInValueState
    val focusManager = LocalFocusManager.current

    SideEffect {
        Log.d("AAAAAAAAA", "state $logInValueState")
    }

    when (user) {
        is Result.LoadingState -> {
            if ((user as Result.LoadingState).name == Result.LoadingState.NotLoading.name) {
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
                            OutlinedTextField(
                                value = logInValueState.email,
                                onValueChange = { viewModel.updateLoginEmail(it) },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_mail),
                                        contentDescription = null,
                                        tint = MaterialTheme.colors.primary.copy(alpha = 0.6F)
                                    )
                                },
                                label = { Text(text = "Email") },
                                placeholder = { Text(text = "Email") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth(0.8f),
                                isError = logInValueState.emailValid == InputState.NotValid,
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Email,
                                    imeAction = ImeAction.Next,
                                ),
                                keyboardActions = KeyboardActions(
                                    onNext = {
                                        focusManager.moveFocus(FocusDirection.Down)
                                    }
                                ),
                            )
                            AnimatedVisibility(visible = logInValueState.emailValid == InputState.NotValid) {
                                Column(
                                    modifier = Modifier.fillMaxWidth(0.8F),
                                    horizontalAlignment = Alignment.End
                                ) {
                                    Text(
                                        text = "正しいメールアドレスではありません",
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colors.error
                                    )
                                }
                            }
                            OutlinedTextField(
                                value = logInValueState.password,
                                onValueChange = { viewModel.updateLoginPassword(it) },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_key),
                                        contentDescription = null,
                                        tint = MaterialTheme.colors.primary.copy(alpha = 0.5F)
                                    )
                                },
                                trailingIcon = {
                                    IconButton(onClick = {
                                        viewModel.toggleLoginPasswordVisibility()
                                    }) {
                                        Icon(
                                            painter =
                                            if (logInValueState.passwordVisibility)
                                                painterResource(id = R.drawable.ic_eye_visibility_on)
                                            else painterResource(
                                                id = R.drawable.ic_eye_visibility_off
                                            ),
                                            tint = if (logInValueState.passwordVisibility) MaterialTheme.colors.primary.copy(
                                                alpha = 0.5F
                                            ) else Color.Gray,
                                            contentDescription = null
                                        )
                                    }
                                },
                                label = { Text("Password") },
                                placeholder = { Text(text = "Password") },
                                singleLine = true,
                                visualTransformation =
                                if (logInValueState.passwordVisibility)
                                    VisualTransformation.None
                                else
                                    PasswordVisualTransformation(),
                                isError = logInValueState.passwordValid == InputState.NotValid,
                                modifier = Modifier.fillMaxWidth(0.8f),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Password,
                                    imeAction = ImeAction.Done,
                                ),
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        focusManager.clearFocus()
                                    }
                                ),
                            )
                            AnimatedVisibility(visible = logInValueState.passwordValid == InputState.NotValid) {
                                Column(
                                    modifier = Modifier.fillMaxWidth(0.8F),
                                    horizontalAlignment = Alignment.End
                                ) {
                                    Text(
                                        text = "パスワードは半角数字英小文字で3～6文字",
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colors.error
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.padding(10.dp))
                            Button(
                                enabled = logInValueState.canRequestLogIn,
                                onClick = {
                                    navController.navigate(Routes.SignUp.route)
                                },
                                shape = RoundedCornerShape(50),
                                modifier = Modifier
                                    .fillMaxWidth(0.8f)
                                    .height(50.dp)
                            ) {
                                Text(text = "Log In", fontSize = 20.sp)
                            }
                        }
                    }
                }
            } else {
                LoadingScreen()
            }
        }
        is Result.Error -> ErrorScreen()
        is Result.Success -> {}
    }
}