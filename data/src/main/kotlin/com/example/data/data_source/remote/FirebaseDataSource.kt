package com.example.data.data_source.remote

import com.example.model.Todo
import com.example.data.utils.Result
import com.example.model.User
import com.google.firebase.auth.FirebaseUser

interface FirebaseDataSource {

    suspend fun getAllTodo(): Result<List<Todo>>

    suspend fun getTodo(id: String): Result<Todo>

    suspend fun addTodo(todo: Todo): Result<String>

    suspend fun updateTodo(id: String, todo: Map<String, Any>): Result<String>

    suspend fun deleteTodo(id: String): Result<String>

    fun getFirebaseUser(): FirebaseUser?

    suspend fun getUser(id: String): Result<User>

    suspend fun registerUser(userName: String, email: String, password: String): Result<User>

    suspend fun logIn(email: String, password: String): Result<User?>
}
