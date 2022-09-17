package com.example.ui.todo

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.FirebaseRepository
import com.example.data.utils.Result
import com.example.model.Todo
import com.example.model.Todo.Companion.toFirebaseObject
import com.example.model.User
import com.example.ui.utils.*
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val repository: FirebaseRepository,
) : ViewModel() {

    private val _todos: MutableStateFlow<UiState<List<Todo>>> = MutableStateFlow(UiState())
    val todos = _todos.asStateFlow()

    private val _todo: MutableStateFlow<UiState<Todo>> = MutableStateFlow(UiState())
    val todo = _todo.asStateFlow()

    private val _firebaseUser: MutableStateFlow<FirebaseUser?> = MutableStateFlow(null)
    val firebaseUser = _firebaseUser.asStateFlow()

    private val _user: MutableStateFlow<Result<User?>> = MutableStateFlow(Result.LoadingState.NotLoading)
    val user = _user.asStateFlow()

    private var job: Job? = null

    var logInValueState: MutableState<LogInValueState> = mutableStateOf(LogInValueState())
        private set

    var signUpValueState: MutableState<SignUpValueState> = mutableStateOf(SignUpValueState())
        private set

    var isFirstLogIn = true
        private set

    init {
        getIsLogin()
    }

    private fun getIsLogin() {
        _firebaseUser.value = repository.getCurrentUser()
    }

    fun getFilteredList(query: String) {
        if (_todos.value.data != null) {
            val filteredList = _todos.value.data?.filter {
                it.title.contains(query) || it.body.contains(query)
            } ?: return
            _todos.value = _todos.value.copy(data = filteredList)
        }
    }

    fun getAllTodo() {
        if (job != null) job?.cancel()
        _todos.startLoading(LoadingState.LOADING)
        job = viewModelScope.launch {
            when (val result = repository.getAllTodo()) {
                is Result.Success -> _todos.handleData(result.data)
                is Result.Error -> _todos.handleError(result.exception)
                is Result.LoadingState -> {}
            }
        }
    }

    fun getTodo(id: String) {
        _todo.startLoading(LoadingState.LOADING)
        viewModelScope.launch {
            when (val result = repository.getTodo(id)) {
                is Result.Success -> _todo.handleData(result.data)
                is Result.Error -> _todo.handleError(result.exception)
                is Result.LoadingState -> {}
            }
        }
    }

    fun addTodo(title: String, body: String, isImportant: Boolean) {
        val todo = Todo(
            title = title,
            body = body,
            authorId = null,
            createdAt = LocalDate.now().toString().replace("-", "/"),
            updateAt = "",
            isImportant = isImportant,
        )
        viewModelScope.launch {
            repository.addTodo(todo)
        }
    }

    fun updateTodo(id: String, title: String, body: String, isImportant: Boolean) {
        val oldTodo = _todo.value.data ?: return
        val newTodo = oldTodo.copy(
            title = title,
            body = body,
            isImportant = isImportant
        )
        viewModelScope.launch {
            repository.updateTodo(id, newTodo.toFirebaseObject())
            getAllTodo()
        }
    }

    fun deleteTodo(id: String) {
        viewModelScope.launch {
            repository.deleteTodo(id)
            getAllTodo()
        }
    }

    fun logIn() {
        _user.value = Result.LoadingState.Loading
        viewModelScope.launch {
            val state = logInValueState.value
            _user.value = repository.logIn(state.email, state.password)
        }
    }

    fun toggleLoginPasswordVisibility() {
        logInValueState.value = logInValueState.value.copy(
            passwordVisibility = !logInValueState.value.passwordVisibility
        )
    }

    fun updateLoginEmail(email: String) {
        val state = logInValueState.value
        logInValueState.value = state.copy(
            email = email,
            emailValid = getInputState(checkIsEmailValid(email)),
            canRequestLogIn =
            getInputState(checkIsEmailValid(email)) == InputState.Valid &&
                state.passwordValid == InputState.Valid
        )
    }

    fun updateLoginPassword(password: String) {
        val state = logInValueState.value
        logInValueState.value = state.copy(
            password = password,
            passwordValid = getInputState(checkIsPasswordValid(password)),
            canRequestLogIn =
            state.emailValid == InputState.Valid && getInputState(checkIsPasswordValid(password)) == InputState.Valid
        )
    }

    fun signUp() {
        _user.value = Result.LoadingState.Loading
        viewModelScope.launch {
            val state = signUpValueState.value
            _user.value = repository.registerUser(state.name, state.email, state.password)
        }
    }

    fun toggleSignUpPasswordVisibility() {
        signUpValueState.value = signUpValueState.value.copy(
            passwordVisibility = !signUpValueState.value.passwordVisibility
        )
    }

    fun toggleSignUpConfirmationPasswordVisibility() {
        signUpValueState.value = signUpValueState.value.copy(
            confirmationPasswordVisibility = !signUpValueState.value.confirmationPasswordVisibility,
        )
    }

    fun updateSignUpName(name: String) {
        val state = signUpValueState.value
        signUpValueState.value = state.copy(
            name = name,
            nameValid = getInputState(checkIsNameValid(name)),
            canRequestSignUp =
            getInputState(checkIsNameValid(name)) == InputState.Valid &&
                state.emailValid == InputState.Valid &&
                state.passwordValid == InputState.Valid &&
                getInputState(state.password == state.confirmationPassword) == InputState.Valid
        )
    }

    fun updateSignUpEmail(email: String) {
        val state = signUpValueState.value
        signUpValueState.value = state.copy(
            email = email,
            emailValid = getInputState(checkIsEmailValid(email)),
            canRequestSignUp =
            getInputState(checkIsEmailValid(email)) == InputState.Valid &&
                state.passwordValid == InputState.Valid &&
                state.nameValid == InputState.Valid &&
                getInputState(state.password == state.confirmationPassword) == InputState.Valid
        )
    }

    fun updateSignUpPassword(password: String) {
        val state = signUpValueState.value
        signUpValueState.value = state.copy(
            password = password,
            passwordValid = getInputState(checkIsPasswordValid(password)),
            canRequestSignUp =
            state.emailValid == InputState.Valid &&
                getInputState(checkIsPasswordValid(password)) == InputState.Valid &&
                state.nameValid == InputState.Valid &&
                getInputState(password == state.confirmationPassword) == InputState.Valid
        )
    }

    fun updateSignUpConfirmationPassword(confirmationPassword: String) {
        val state = signUpValueState.value
        signUpValueState.value = state.copy(
            confirmationPassword = confirmationPassword,
            confirmationPasswordValid = getInputState(state.password == confirmationPassword),
            canRequestSignUp =
            state.emailValid == InputState.Valid &&
                getInputState(state.password == confirmationPassword) == InputState.Valid &&
                state.nameValid == InputState.Valid &&
                state.passwordValid == InputState.Valid
        )
    }

    fun setIsFirstLogIn() {
        isFirstLogIn = false
    }
}