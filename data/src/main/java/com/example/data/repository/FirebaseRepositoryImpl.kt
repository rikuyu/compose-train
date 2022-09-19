package com.example.data.repository

import com.example.data.data_source.remote.FirebaseDataSource
import com.example.data.utils.Result
import com.example.model.Todo
import com.example.model.User
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource,
) : FirebaseRepository {

    override suspend fun getAllTodo(): Result<List<Todo>> = firebaseDataSource.getAllTodo()

    override suspend fun getTodo(id: String): Result<Todo> = firebaseDataSource.getTodo(id)

    override suspend fun addTodo(todo: Todo): Result<String> = firebaseDataSource.addTodo(todo)

    override suspend fun updateTodo(id: String, todo: Map<String, Any>): Result<String> =
        firebaseDataSource.updateTodo(id, todo)

    override suspend fun deleteTodo(id: String): Result<String> = firebaseDataSource.deleteTodo(id)

    override fun getFirebaseUser(): FirebaseUser? = firebaseDataSource.getFirebaseUser()

    override suspend fun getUser(id: String): Result<User?> = firebaseDataSource.getUser(id)

    override suspend fun registerUser(userName: String, email: String, password: String): Result<User> =
        firebaseDataSource.registerUser(userName, email, password)

    override suspend fun logIn(email: String, password: String): Result<User?> =
        firebaseDataSource.logIn(email, password)
}