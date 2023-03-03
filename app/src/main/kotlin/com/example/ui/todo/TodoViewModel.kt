package com.example.ui.todo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.FirebaseRepository
import com.example.data.utils.Result
import com.example.model.Todo
import com.example.model.Todo.Companion.toFirebaseObject
import com.example.model.TodoData
import com.example.model.User
import com.example.ui.utils.checkIsEmailValid
import com.example.ui.utils.checkIsNameValid
import com.example.ui.utils.checkIsPasswordValid
import com.example.ui.utils.getInputState
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val repository: FirebaseRepository,
) : ViewModel() {

    private val _todo: MutableStateFlow<TodoUiState<Todo>> = MutableStateFlow(TodoUiState())
    val todo = _todo.asStateFlow()

    private val _firebaseUser: MutableStateFlow<FirebaseUser?> = MutableStateFlow(null)
    val firebaseUser = _firebaseUser.asStateFlow()

    private val _todos: MutableStateFlow<Result<List<Todo>>> = MutableStateFlow(Result.LoadingState.Loading)

    private val _user: MutableStateFlow<Result<User?>> = MutableStateFlow(Result.LoadingState.NotLoading)
    val user = _user.asStateFlow()

    private var job: Job? = null

    var logInValueState: MutableState<LogInValueState> = mutableStateOf(LogInValueState())
        private set

    var signUpValueState: MutableState<SignUpValueState> = mutableStateOf(SignUpValueState())
        private set

    var isFirstLogIn = true
        private set

    val todosData: StateFlow<Result<TodoData>> = combine(
        _user, _todos
    ) { user, todos ->
        val data = if (user is Result.LoadingState || todos is Result.LoadingState) {
            Result.LoadingState.Loading
        } else if (user is Result.Error) {
            Result.Error(user.exception)
        } else if (todos is Result.Error) {
            Result.Error(todos.exception)
        } else if (user is Result.Success && todos is Result.Success) {
            Result.Success(TodoData(todos = todos.data, user = user.data))
        } else {
            Result.Error(Throwable("Error"))
        }
        return@combine data
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = Result.LoadingState.Loading
    )

    init {
        getIsLogin()
    }

    fun getUserData(id: String?) {
        if (id == null) {
            _user.value = Result.Error(Exception())
            return
        }
        viewModelScope.launch {
            _user.value = Result.LoadingState.Loading
            _user.value = when (val result = repository.getUser(id)) {
                is Result.LoadingState -> Result.LoadingState.Loading
                is Result.Error -> Result.Error(result.exception)
                is Result.Success -> Result.Success(result.data)
            }
        }
    }

    fun getAllTodo() {
        if (job != null) job?.cancel()
        _todos.value = Result.LoadingState.Loading
        job = viewModelScope.launch {
            _todos.value = when (val result = repository.getAllTodo()) {
                is Result.LoadingState -> Result.LoadingState.Loading
                is Result.Error -> Result.Error(result.exception)
                is Result.Success -> Result.Success(result.data)
            }
        }
    }

    private fun getIsLogin() {
        _firebaseUser.value = repository.getFirebaseUser()
    }

    fun getFilteredList(query: String) {
//        if (_todos.value.data != null) {
//            val filteredList = _todos.value.data?.filter {
//                it.title.contains(query) || it.body.contains(query)
//            } ?: return
//            _todos.value = _todos.value.copy(data = filteredList)
//        }
    }

    fun getTodo(id: String) {
        viewModelScope.launch {
            _todo.startLoading(LoadingState.LOADING)
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

@Stable
data class TodoUiState<T>(
    val isLoading: LoadingState = LoadingState.NOT_LOADING,
    val data: T? = null,
    val error: Throwable? = null,
) {
    @Composable
    fun StateView(
        loadingView: (@Composable () -> Unit)?,
        errorView: (@Composable (error: Throwable) -> Unit)?,
        successView: @Composable (data: T) -> Unit,
    ) {
        if (isLoading == LoadingState.LOADING) {
            if (loadingView != null) loadingView()
        } else if (error != null) {
            if (errorView != null) errorView(error)
        } else if (data != null) {
            successView(data)
        }
    }

    val isRefreshing: Boolean get() = isLoading == LoadingState.REFRESHING
}

fun <T> MutableStateFlow<TodoUiState<T>>.startLoading(loadingState: LoadingState) {
    value = value.copy(isLoading = loadingState)
}

fun <T> MutableStateFlow<TodoUiState<T>>.handleData(data: T) {
    value = value.copy(
        isLoading = LoadingState.NOT_LOADING,
        data = data,
        error = null
    )
}

fun <T> MutableStateFlow<TodoUiState<T>>.handleError(error: Throwable) {
    value = value.copy(
        isLoading = LoadingState.NOT_LOADING,
        error = error
    )
}

enum class LoadingState {
    LOADING, NOT_LOADING, REFRESHING,
}