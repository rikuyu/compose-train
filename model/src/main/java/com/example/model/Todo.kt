package com.example.model

import androidx.compose.runtime.Stable
import java.util.*

@Stable
data class Todo(
    var id: String = UUID.randomUUID().toString(),
    var title: String = "",
    var body: String = "",
    var createdAt: String = "",
    var updateAt: String = "",
    var isImportant: Boolean = false,
) {
    companion object {
        fun Todo.toFirebaseObject() = hashMapOf<String, Any>(
            "id" to id,
            "title" to title,
            "body" to body,
            "createdAt" to createdAt,
            "updateAt" to updateAt,
            "important" to isImportant,
        )
    }
}

@Stable
data class TodoData(val todos: List<Todo>, val user: User?)
