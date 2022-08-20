package com.example.ui.todo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.FirebaseRepository
import com.example.data.utils.Result
import com.example.model.Author
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
       list.forEach {
           Log.d("AAAAAAAAAAA", "id ${it.id}")
       }
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

private val list = listOf(
    Todo(
        id = 1,
        title = "掃除",
        body = "部屋をきれいにする",
        author = Author(1, "Takashi", "1 image"),
        createdAt = "2022/11/11",
        updateAt = "2022/11/11",
        isImportant = false,
    ),
    Todo(
        id = 2,
        title = "勉強",
        body = "がんばって○○を学ぶ",
        author = Author(2, "Ken", "2 image"),
        createdAt = "2022/10/08",
        updateAt = "2022/10/08",
        isImportant = true,
    ),
    Todo(
        id = 3,
        title = "買い物",
        body = "スーパーに野菜と肉を買いに行く",
        author = Author(3, "Sakura", "3 image"),
        createdAt = "2022/04/23",
        updateAt = "2022/04/23",
        isImportant = false,
    ),
    Todo(
        id = 4,
        title = "料理",
        body = "夜食の作り置きを用意する",
        author = Author(4, "Nao", "4 image"),
        createdAt = "2022/12/01",
        updateAt = "2022/12/01",
        isImportant = true,
    ),
    Todo(
        id = 5,
        title = "洗濯",
        body = "天気が良いのでまとめて選択する",
        author = Author(5, "Ami", "5 image"),
        createdAt = "2022/05/15",
        updateAt = "2022/05/15",
        isImportant = false,
    ),
    Todo(
        id = 6,
        title = "読書",
        body = "技術書をたくさん読む",
        author = Author(2, "Ken", "2 image"),
        createdAt = "2022/05/15",
        updateAt = "2022/05/15",
        isImportant = false,
    ),
    Todo(
        id = 7,
        title = "お風呂掃除",
        body = "水垢をとる",
        author = Author(5, "Ami", "5 image"),
        createdAt = "2022/05/16",
        updateAt = "2022/05/16",
        isImportant = false,
    ),
    Todo(
        id = 8,
        title = "トイレ掃除",
        body = "水垢をとる",
        author = Author(5, "Ami", "5 image"),
        createdAt = "2022/05/16",
        updateAt = "2022/05/16",
        isImportant = false,
    ),
    Todo(
        id = 9,
        title = "靴磨き",
        body = "お気に入りの靴をきれいにする",
        author = Author(2, "Ken", "2 image"),
        createdAt = "2022/05/16",
        updateAt = "2022/05/16",
        isImportant = false,
    ),
)