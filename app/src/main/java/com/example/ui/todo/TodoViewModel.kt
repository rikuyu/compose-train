package com.example.ui.todo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.FirebaseRepository
import com.example.data.utils.Result
import com.example.model.Todo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val repository: FirebaseRepository,
) : ViewModel() {

    private val _todos: MutableStateFlow<Result<List<Todo>>> = MutableStateFlow(Result.Loading)
    val todos: StateFlow<Result<List<Todo>>> = _todos.asStateFlow()

    private val _todo: MutableStateFlow<Result<Todo>> = MutableStateFlow(Result.Loading)
    val todo: StateFlow<Result<Todo>> = _todo.asStateFlow()

    private val _result: MutableStateFlow<Result<String>> = MutableStateFlow(Result.Loading)
    val result: StateFlow<Result<String>> = _result.asStateFlow()

    init {
        Log.d("AAAAAAAAA", "init")
        getAllTodo()
    }

    fun getFilteredList(query: String) {
        if (_todos.value is Result.Success) {
            val filteredList = (_todos.value as Result.Success<List<Todo>>).data.filter {
                it.title.contains(query) || it.body.contains(query)
            }
            _todos.value = Result.Success(filteredList)
        }
    }

    fun getAllTodo() {
        _todos.value = Result.Loading
        viewModelScope.launch {
            _todos.value = repository.getAllTodo()
        }
    }

    fun getTodo(id: Long) {
        viewModelScope.launch {
            _todo.value = repository.getTodo(id)
        }
    }

    fun addTodo(todo: Todo) {
        _result.value = Result.Loading
        viewModelScope.launch {
            _result.value = repository.addTodo(todo)
        }
    }

    fun updateTodo(id: Long, todo: Map<String, Any>) {
        _result.value = Result.Loading
        viewModelScope.launch {
            _result.value = repository.updateTodo(id, todo)
        }
    }

    fun deleteTodo(id: Long) {
        _result.value = Result.Loading
        viewModelScope.launch {
            _result.value = repository.deleteTodo(id)
        }
    }
}