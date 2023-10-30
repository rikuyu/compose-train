package com.example.data.data_source.remote

import com.example.data.utils.Result
import com.example.model.Todo
import com.example.model.User
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseDataSourceImpl @Inject constructor() : FirebaseDataSource {

    private val fireStore = Firebase.firestore
    private val auth = Firebase.auth

    override suspend fun getAllTodo(): Result<List<Todo>> {
        var result: Result<List<Todo>> = Result.LoadingState.Loading
        runCatching { fireStore.collection(TODO_COLLECTION).get().await() }
            .onSuccess {
                val todos = it.map { snapshot ->
                    snapshot.toObject<Todo>()
                }
                result = Result.Success(todos)
            }
            .onFailure {
                result = Result.Error(it)
            }
        return result
    }

    override suspend fun getTodo(id: String): Result<Todo> {
        var result: Result<Todo> = Result.LoadingState.Loading
        runCatching {
            fireStore.collection(TODO_COLLECTION)
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
            .onFailure { result = Result.Error(it) }
        return result
    }

    override suspend fun addTodo(todo: Todo): Result<String> {
        var result: Result<String> = Result.LoadingState.Loading
        runCatching {
            fireStore.collection(TODO_COLLECTION)
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
            fireStore.collection(TODO_COLLECTION)
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
            fireStore.collection(TODO_COLLECTION)
                .document(id)
                .delete()
                .await()
        }
            .onSuccess { result = Result.Success(MESSAGE_SUCCESS) }
            .onFailure { result = Result.Error(it) }
        return result
    }

    override fun getFirebaseUser(): FirebaseUser? = auth.currentUser

    override suspend fun getUser(id: String): Result<User> {
        var result: Result<User> = Result.LoadingState.Loading
        runCatching {
            fireStore.collection(USER_COLLECTION)
                .document(id)
                .get()
                .await()
        }
            .onSuccess {
                val user = it.toObject<User>()
                result = if (user != null) {
                    Result.Success(user)
                } else {
                    Result.Error(Exception("User not found"))
                }
            }
            .onFailure { result = Result.Error(it) }
        return result
    }

    override suspend fun registerUser(userName: String, email: String, password: String): Result<User> {
        var result: Result<User> = Result.LoadingState.Loading
        runCatching {
            auth.createUserWithEmailAndPassword(email, password).await()
        }
            .onSuccess {
                val userId = auth.currentUser?.uid ?: return Result.Error(Exception())
                val user = mapOf("id" to userId, "name" to userName, "email" to email)
                runCatching {
                    fireStore.collection(USER_COLLECTION)
                        .document(userId)
                        .set(user)
                        .await()
                }
                    .onSuccess { result = Result.Success(User(userId, userName, email)) }
                    .onFailure { result = Result.Error(it) }
            }
            .onFailure { result = Result.Error(it) }
        return result
    }

    override suspend fun logIn(email: String, password: String): Result<User?> {
        var result: Result<User?> = Result.LoadingState.Loading
        runCatching {
            auth.signInWithEmailAndPassword(email, password).await()
        }
            .onSuccess {
                val userId = auth.currentUser?.uid ?: return Result.Error(Exception())
                runCatching {
                    fireStore.collection(USER_COLLECTION)
                        .document(userId)
                        .get()
                        .await()
                }
                    .onSuccess {
                        result = Result.Success(it.toObject<User>())
                    }
                    .onFailure {
                        result = Result.Error(Exception())
                    }
            }
            .onFailure { result = Result.Error(it) }
        return result
    }

    companion object {
        private const val TODO_COLLECTION = "todo"
        private const val USER_COLLECTION = "user"
        private const val MESSAGE_SUCCESS = "success"
    }
}
