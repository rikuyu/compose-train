package com.example.domain.repository

import com.example.domain.model.Todo

interface FirebaseRepository {

    suspend fun getAllTodo(): List<Todo>

    suspend fun getTodo(id: Long): Todo

    suspend fun addTodo(todo: Todo)

    suspend fun updateTodo(id: Long, title: String, body: String)

    suspend fun deleteTodo(id: Long)
}