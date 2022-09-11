package com.example.ui.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.FirebaseRepository
import com.example.data.utils.Result
import com.example.model.Todo
import com.example.model.Todo.Companion.toFirebaseObject
import com.example.ui.utils.*
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

    private var job: Job? = null

    init {
        getAllTodo()
    }

    fun getFilteredList(query: String) {
        if (_todos.value.data != null) {
            val filteredList = _todos.value.data?.filter {
                it.title.contains(query) || it.body.contains(query)
            } ?: return
            _todos.value = _todos.value.copy(data = filteredList)
        }
    }

    private fun getAllTodo() {
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
}