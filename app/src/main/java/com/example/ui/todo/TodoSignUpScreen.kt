package com.example.ui.todo

import android.view.KeyEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }

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
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
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
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .onKeyEvent {
                            if (it.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
                            }
                            true
                        },
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
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
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .onKeyEvent {
                            if (it.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
                            }
                            true
                        },
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_key),
                            contentDescription = null,
                            tint = MaterialTheme.colors.primary.copy(alpha = 0.5F)
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = {
                            passwordVisibility = !passwordVisibility
                        }) {
                            Icon(
                                painter =
                                if (passwordVisibility)
                                    painterResource(id = R.drawable.ic_eye_visibility_on)
                                else painterResource(
                                    id = R.drawable.ic_eye_visibility_off
                                ),
                                tint = if (passwordVisibility) MaterialTheme.colors.primary.copy(alpha = 0.5F) else Color.Gray,
                                contentDescription = null
                            )
                        }
                    },
                    label = { Text("Password") },
                    placeholder = { Text(text = "Password") },
                    singleLine = true,
                    visualTransformation = if (passwordVisibility) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                    // .focusRequester(focusRequester = focusRequester),
                )
                Spacer(modifier = Modifier.padding(10.dp))
                Button(
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