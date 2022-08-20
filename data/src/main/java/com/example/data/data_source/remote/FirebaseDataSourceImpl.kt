package com.example.data.data_source.remote

import com.example.data.utils.Result
import com.example.model.Todo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class FirebaseDataSourceImpl @Inject constructor() : FirebaseDataSource {

    private val firestore = Firebase.firestore

    override suspend fun getAllTodo(): Result<List<Todo>> {
        var result: Result<List<Todo>> = Result.Loading
        firestore.collection(TODO_COLLECTION)
            .get()
            .addOnSuccessListener {
                val todos = mutableListOf<Todo>()
                it.forEach {
                    val todo = it.toObject<Todo>()
                    todos.add(todo)
                }
                result = Result.Success(todos.toList())
            }
            .addOnFailureListener { result = Result.Error(it) }
        return result
    }

    override suspend fun getTodo(id: Long): Result<Todo> {
        var result: Result<Todo> = Result.Loading
        firestore.collection(TODO_COLLECTION)
            .document(id.toString())
            .get()
            .addOnSuccessListener {
                val todo = it.toObject<Todo>()
                result = if (todo != null) {
                    Result.Success(todo)
                } else {
                    Result.Error(Exception("Todo not found"))
                }
            }
            .addOnFailureListener { result = Result.Error(it) }
        return result
    }

    override suspend fun addTodo(todo: Todo): Result<String> {
        var result: Result<String> = Result.Loading
        firestore.collection(TODO_COLLECTION)
            .document(todo.id.toString())
            .set(todo)
            .addOnSuccessListener { result = Result.Success(MESSAGE_SUCCESS) }
            .addOnFailureListener { result = Result.Error(it) }
        return result
    }

    override suspend fun updateTodo(id: Long, todo: Map<String, Any>): Result<String> {
        var result: Result<String> = Result.Loading
        firestore.collection(TODO_COLLECTION)
            .document(id.toString())
            .update(todo)
            .addOnSuccessListener { result = Result.Success(MESSAGE_SUCCESS) }
            .addOnFailureListener { result = Result.Error(it) }
        return result
    }

    override suspend fun deleteTodo(id: Long): Result<String> {
        var result: Result<String> = Result.Loading
        firestore.collection(TODO_COLLECTION)
            .document(id.toString())
            .delete()
            .addOnSuccessListener { result = Result.Success(MESSAGE_SUCCESS) }
            .addOnFailureListener { result = Result.Error(it) }
        return result
    }

    companion object {
        private const val TODO_COLLECTION = "todo"
        private const val MESSAGE_SUCCESS = "success"
    }
}