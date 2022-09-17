package com.example.ui.todo

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
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

fun NavGraphBuilder.addSignUp(
    navController: NavHostController,
    changeScreen: () -> Unit,
) {
    composable(route = Routes.SignUp.route) {
        changeScreen()
        TodoSignUpScreen(navController)
    }
}

@Composable
fun TodoSignUpScreen(
    navController: NavController,
    viewModel: TodoViewModel = hiltViewModel()
) {
    val signUpValueState by viewModel.signUpValueState
    val focusManager = LocalFocusManager.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.image_signup),
            contentDescription = null,
            modifier = Modifier.fillMaxHeight(0.2F)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Sign Up",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    // letterSpacing = TextUnit.Companion.Sp(2)
                ),
                fontSize = 26.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                OutlinedTextField(
                    value = signUpValueState.name,
                    onValueChange = { viewModel.updateSignUpName(it) },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_account),
                            contentDescription = null,
                            tint = MaterialTheme.colors.primary.copy(alpha = 0.6F)
                        )
                    },
                    label = { Text(text = "UserName") },
                    placeholder = { Text(text = "UserName") },
                    singleLine = true,
                    isError = signUpValueState.nameValid == InputState.NotValid,
                    modifier = Modifier.fillMaxWidth(0.8f),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                )
                AnimatedVisibility(visible = signUpValueState.nameValid == InputState.NotValid) {
                    Column(
                        modifier = Modifier.fillMaxWidth(0.8F),
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = "名前は2～5文字",
                            fontSize = 14.sp,
                            color = MaterialTheme.colors.error
                        )
                    }
                }
                OutlinedTextField(
                    value = signUpValueState.email,
                    onValueChange = { viewModel.updateSignUpEmail(it) },
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
                    isError = signUpValueState.emailValid == InputState.NotValid,
                    modifier = Modifier.fillMaxWidth(0.8f),
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
                AnimatedVisibility(visible = signUpValueState.emailValid == InputState.NotValid) {
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
                    value = signUpValueState.password,
                    onValueChange = { viewModel.updateSignUpPassword(it) },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_key),
                            contentDescription = null,
                            tint = MaterialTheme.colors.primary.copy(alpha = 0.5F)
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = {
                            viewModel.toggleSignUpPasswordVisibility()
                        }) {
                            Icon(
                                painter =
                                if (signUpValueState.passwordVisibility)
                                    painterResource(id = R.drawable.ic_eye_visibility_on)
                                else painterResource(
                                    id = R.drawable.ic_eye_visibility_off
                                ),
                                tint = if (signUpValueState.passwordVisibility) MaterialTheme.colors.primary.copy(alpha = 0.5F) else Color.Gray,
                                contentDescription = null
                            )
                        }
                    },
                    label = { Text("Password") },
                    placeholder = { Text(text = "Password") },
                    singleLine = true,
                    visualTransformation = if (signUpValueState.passwordVisibility) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(0.8f),
                    isError = signUpValueState.passwordValid == InputState.NotValid,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next,
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                )
                AnimatedVisibility(visible = signUpValueState.passwordValid == InputState.NotValid) {
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
                OutlinedTextField(
                    value = signUpValueState.confirmationPassword,
                    onValueChange = { viewModel.updateSignUpConfirmationPassword(it) },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_key),
                            contentDescription = null,
                            tint = MaterialTheme.colors.primary.copy(alpha = 0.5F)
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = {
                            viewModel.toggleSignUpConfirmationPasswordVisibility()
                        }) {
                            Icon(
                                painter =
                                if (signUpValueState.confirmationPasswordVisibility)
                                    painterResource(id = R.drawable.ic_eye_visibility_on)
                                else painterResource(
                                    id = R.drawable.ic_eye_visibility_off
                                ),
                                tint = if (signUpValueState.confirmationPasswordVisibility) MaterialTheme.colors.primary.copy(
                                    alpha = 0.5F
                                ) else Color.Gray,
                                contentDescription = null
                            )
                        }
                    },
                    label = { Text("Confirmation Password") },
                    placeholder = { Text(text = "Confirmation Password") },
                    singleLine = true,
                    visualTransformation = if (signUpValueState.confirmationPasswordVisibility) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(0.8f),
                    isError = signUpValueState.confirmationPasswordValid == InputState.NotValid,
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
                AnimatedVisibility(visible = signUpValueState.confirmationPasswordValid == InputState.NotValid) {
                    Column(
                        modifier = Modifier.fillMaxWidth(0.8F),
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = "パスワードが等しくありません",
                            fontSize = 14.sp,
                            color = MaterialTheme.colors.error
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(10.dp))
                Button(
                    enabled = signUpValueState.canRequestSignUp,
                    onClick = {
                    },
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(50.dp)
                ) {
                    Text(text = "Sign Up", fontSize = 20.sp)
                }
            }
        }
    }
}