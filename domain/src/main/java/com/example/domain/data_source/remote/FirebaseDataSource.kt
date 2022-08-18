package com.example.domain.data_source.remote

import com.example.domain.model.Todo
import com.example.data.utils.Result

interface FirebaseDataSource {

    suspend fun getAllTodo(): Result<List<Todo>>

    suspend fun getTodo(id: Long): Result<Todo>

    suspend fun addTodo(todo: Todo): Result<String>

    suspend fun updateTodo(id: Long, todo: Map<String, Any>): Result<String>

    suspend fun deleteTodo(id: Long): Result<String>
}