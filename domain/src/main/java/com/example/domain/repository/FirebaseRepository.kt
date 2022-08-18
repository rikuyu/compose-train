package com.example.domain.repository

import com.example.domain.model.Todo
import com.example.data.utils.Result

interface FirebaseRepository {

    suspend fun getAllTodo(): Result<List<Todo>>

    suspend fun getTodo(id: Long): Result<Todo>

    suspend fun addTodo(todo: Todo): Result<String>

    suspend fun updateTodo(id: Long, todo: Map<String, Any>): Result<String>

    suspend fun deleteTodo(id: Long): Result<String>
}