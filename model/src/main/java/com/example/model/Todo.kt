package com.example.model

import androidx.compose.runtime.Stable
import java.util.*

@Stable
data class Todo(
    var id: String = UUID.randomUUID().toString(),
    var title: String = "",
    var body: String = "",
    var author: Author? = null,
    var createdAt: String = "",
    var updateAt: String = "",
    var isImportant: Boolean = false,
) {
    companion object {
        fun Todo.toMap(
            id: String,
            title: String?,
            body: String?,
            authorId: Long,
            createdAt: String?,
            updateAt: String?,
            isImportant: Boolean?,
        ) = hashMapOf<String, Any>(
            "id" to id,
            "title" to (title ?: this.title),
            "body" to (body ?: this.body),
            "authorId" to authorId.toString(),
            "createdAt" to (createdAt ?: this.createdAt),
            "updateAt" to (updateAt ?: this.updateAt),
            "isImportant" to (isImportant ?: this.isImportant),
        )
    }
}