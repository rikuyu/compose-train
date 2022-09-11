package com.example.data.data_source.remote

import android.util.Log
import com.example.data.utils.Result
import com.example.model.Todo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseDataSourceImpl @Inject constructor() : FirebaseDataSource {

    private val firestore = Firebase.firestore

    override suspend fun getAllTodo(): Result<List<Todo>> {
        var result: Result<List<Todo>> = Result.LoadingState.Loading
        runCatching { firestore.collection(TODO_COLLECTION).get().await() }
            .onSuccess {
                val todos = mutableListOf<Todo>()
                it.forEach { snapshot ->
                    val todo = snapshot.toObject<Todo>()
                    todos.add(todo)
                }
                result = Result.Success(todos.toList())
            }
            .onFailure {
                result = Result.Error(it)
            }
        return result
    }

    override suspend fun getTodo(id: String): Result<Todo> {
        var result: Result<Todo> = Result.LoadingState.Loading
        runCatching {
            firestore.collection(TODO_COLLECTION)
                .document(id)
                .get()
                .await()
        }
            .onSuccess {
                val todo = it.toObject<Todo>()
                result = if (todo != null) {
                    Result.Success(todo)
                } else {
                    Result.Error(Exception("Todo not found"))
                }
            }
            .onFailure {
                result = Result.Error(it)
            }
        return result
    }

    override suspend fun addTodo(todo: Todo): Result<String> {
        var result: Result<String> = Result.LoadingState.Loading
        runCatching {
            firestore.collection(TODO_COLLECTION)
                .document(todo.id)
                .set(todo)
                .await()
        }
            .onSuccess { result = Result.Success(MESSAGE_SUCCESS) }
            .onFailure { result = Result.Error(it) }
        return result
    }

    override suspend fun updateTodo(id: String, todo: Map<String, Any>): Result<String> {
        var result: Result<String> = Result.LoadingState.Loading
        runCatching {
            firestore.collection(TODO_COLLECTION)
                .document(id)
                .update(todo)
                .await()
        }
            .onSuccess { result = Result.Success(MESSAGE_SUCCESS) }
            .onFailure { result = Result.Error(it) }
        return result
    }

    override suspend fun deleteTodo(id: String): Result<String> {
        var result: Result<String> = Result.LoadingState.Loading
        runCatching {
            firestore.collection(TODO_COLLECTION)
                .document(id)
                .delete()
                .await()
        }
            .onSuccess { result = Result.Success(MESSAGE_SUCCESS) }
            .onFailure { result = Result.Error(it) }
        return result
    }

    companion object {
        private const val TODO_COLLECTION = "todo"
        private const val MESSAGE_SUCCESS = "success"
    }
}