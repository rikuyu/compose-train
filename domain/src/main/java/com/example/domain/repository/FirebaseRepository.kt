package com.example.domain.repository

import com.example.domain.model.Todo

interface FirebaseRepository {

    suspend fun getAllTodo(): Result<List<Todo>>

    suspend fun getTodo(id: Long): Result<Todo>

    suspend fun addTodo(todo: Todo): Result<Nothing>

    suspend fun updateTodo(id: Long, title: String, body: String): Result<Nothing>

    suspend fun deleteTodo(id: Long): Result<Nothing>
}