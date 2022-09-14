package com.example.data.repository

import com.example.model.Todo
import com.example.data.utils.Result
import com.example.model.User
import com.google.firebase.auth.FirebaseUser

interface FirebaseRepository {

    suspend fun getAllTodo(): Result<List<Todo>>

    suspend fun getTodo(id: String): Result<Todo>

    suspend fun addTodo(todo: Todo): Result<String>

    suspend fun updateTodo(id: String, todo: Map<String, Any>): Result<String>

    suspend fun deleteTodo(id: String): Result<String>

    fun getCurrentUser(): FirebaseUser?

    suspend fun registerUser(userName: String, email: String, password: String): Result<String>

    suspend fun logIn(email: String, password: String): Result<User?>
}