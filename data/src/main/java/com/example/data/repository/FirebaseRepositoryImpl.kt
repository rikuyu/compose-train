package com.example.data.repository

import com.example.data.utils.Result
import com.example.domain.data_source.remote.FirebaseDataSource
import com.example.domain.model.Todo
import com.example.domain.repository.FirebaseRepository
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource,
) : FirebaseRepository {

    override suspend fun getAllTodo(): Result<List<Todo>> = firebaseDataSource.getAllTodo()

    override suspend fun getTodo(id: Long): Result<Todo> = firebaseDataSource.getTodo(id)

    override suspend fun addTodo(todo: Todo): Result<String> = firebaseDataSource.addTodo(todo)

    override suspend fun updateTodo(id: Long, todo: Map<String, Any>): Result<String> =
        firebaseDataSource.updateTodo(id, todo)

    override suspend fun deleteTodo(id: Long): Result<String> = firebaseDataSource.deleteTodo(id)
}