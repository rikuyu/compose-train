package com.example.model

import com.google.firebase.firestore.DocumentId

data class Todo(
    @DocumentId val id: Long,
    val title: String,
    val body: String,
    val author: Author,
    val createdAt: String,
    val updateAt: String,
    val isImportant: Boolean = false,
) {
    companion object {
        fun Todo.toMap(
            id: Long,
            title: String?,
            body: String?,
            authorId: Long,
            createdAt: String?,
            updateAt: String?,
            isImportant: Boolean?,
        ) = hashMapOf<String, Any>(
            "id" to id.toString(),
            "title" to (title ?: this.title),
            "body" to (body ?: this.body),
            "authorId" to authorId.toString(),
            "createdAt" to (createdAt ?: this.createdAt),
            "updateAt" to (updateAt ?: this.updateAt),
            "isImportant" to (isImportant ?: this.isImportant),
        )
    }
}