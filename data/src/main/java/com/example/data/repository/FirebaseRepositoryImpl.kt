package com.example.data.repository

import com.example.domain.model.Todo
import com.example.domain.repository.FirebaseRepository
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor() : FirebaseRepository {

    override suspend fun getAllTodo(): List<Todo> {
        TODO("Not yet implemented")
    }

    override suspend fun getTodo(id: Long): Todo {
        TODO("Not yet implemented")
    }

    override suspend fun addTodo(todo: Todo) {
        TODO("Not yet implemented")
    }

    override suspend fun updateTodo(id: Long, title: String, body: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTodo(id: Long) {
        TODO("Not yet implemented")
    }
}