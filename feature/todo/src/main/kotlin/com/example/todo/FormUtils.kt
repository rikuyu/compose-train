package com.example.todo

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import com.example.feature.todo.R
import java.util.regex.Pattern

@Composable
fun ColumnScope.ErrorMessage(text: String, flag: Boolean) {
    AnimatedVisibility(visible = flag) {
        Column(
            modifier = Modifier.fillMaxWidth(0.8F),
            horizontalAlignment = Alignment.End,
        ) {
            Text(
                text = text,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.error,
            )
        }
    }
}

@Composable
fun NameForm(
    name: String,
    isError: Boolean,
    onNext: () -> Unit,
    onNameChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = name,
        onValueChange = { onNameChange(it) },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_account),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
            )
        },
        label = { Text(text = "UserName", color = MaterialTheme.colorScheme.primary) },
        placeholder = { Text(text = "UserName", color = MaterialTheme.colorScheme.primary) },
        singleLine = true,
        isError = isError,
        modifier = Modifier.fillMaxWidth(0.8f),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next,
        ),
        keyboardActions = KeyboardActions(
            onNext = { onNext() },
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = MaterialTheme.colorScheme.onBackground,
        ),
    )
}

@Composable
fun EmailForm(
    email: String,
    isError: Boolean,
    onNext: () -> Unit,
    onEmailChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = email,
        onValueChange = { onEmailChange(it) },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_mail),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
            )
        },
        label = { Text(text = "Email", color = MaterialTheme.colorScheme.primary) },
        placeholder = { Text(text = "Email", color = MaterialTheme.colorScheme.primary) },
        singleLine = true,
        isError = isError,
        modifier = Modifier.fillMaxWidth(0.8f),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next,
        ),
        keyboardActions = KeyboardActions(
            onNext = { onNext() },
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = MaterialTheme.colorScheme.onBackground,
        ),
    )
}

@Composable
fun PasswordForm(
    label: String = "Password",
    password: String,
    isError: Boolean,
    visibility: Boolean,
    toggleVisibility: () -> Unit,
    onNext: (() -> Unit)? = null,
    onDone: (() -> Unit)? = null,
    onPasswordChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = password,
        onValueChange = { onPasswordChange(it) },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_key),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
            )
        },
        trailingIcon = {
            IconButton(onClick = {
                toggleVisibility()
            }) {
                Icon(
                    painter =
                    if (visibility) {
                        painterResource(id = R.drawable.ic_eye_visibility_on)
                    } else {
                        painterResource(
                            id = R.drawable.ic_eye_visibility_off,
                        )
                    },
                    tint = if (visibility) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        Color.Gray
                    },
                    contentDescription = null,
                )
            }
        },
        label = { Text(text = label, color = MaterialTheme.colorScheme.primary) },
        placeholder = { Text(text = label, color = MaterialTheme.colorScheme.primary) },
        singleLine = true,
        visualTransformation =
        if (visibility) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        isError = isError,
        modifier = Modifier.fillMaxWidth(0.8f),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(
            onNext = { onNext?.invoke() },
            onDone = { onDone?.invoke() },
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = MaterialTheme.colorScheme.onBackground,
        ),
    )
}

private const val EMAIL_VALIDATION_REGEX = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.+[a-z]+$"

private const val PASSWORD_VALIDATION_REGEX = "[a-z0-9]{6,10}"

fun checkIsEmailValid(email: String) = Pattern.matches(EMAIL_VALIDATION_REGEX, email)

fun checkIsPasswordValid(password: String) = Pattern.matches(PASSWORD_VALIDATION_REGEX, password)

fun getInputState(isValid: Boolean) = if (isValid) InputState.Valid else InputState.NotValid

fun checkIsNameValid(name: String) = name.length in 2..5

fun checkIsTodoTitleValid(title: String) = title.length in 2..10

fun checkIsTodoBodyValid(body: String) = body.length in 3..50
