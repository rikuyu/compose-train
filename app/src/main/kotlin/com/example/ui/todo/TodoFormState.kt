package com.example.ui.todo

import androidx.compose.runtime.Stable

@Stable
data class LogInValueState(
    val email: String = "",
    val emailValid: InputState = InputState.Initial,
    val password: String = "",
    val passwordValid: InputState = InputState.Initial,
    val passwordVisibility: Boolean = false,
    val canRequestLogIn: Boolean = false,
)

@Stable
data class SignUpValueState(
    val name: String = "",
    val nameValid: InputState = InputState.Initial,
    val email: String = "",
    val emailValid: InputState = InputState.Initial,
    val password: String = "",
    val passwordValid: InputState = InputState.Initial,
    val passwordVisibility: Boolean = false,
    val confirmationPassword: String = "",
    val confirmationPasswordValid: InputState = InputState.Initial,
    val confirmationPasswordVisibility: Boolean = false,
    val canRequestSignUp: Boolean = false,
)

enum class InputState {
    Initial, NotValid, Valid
}
