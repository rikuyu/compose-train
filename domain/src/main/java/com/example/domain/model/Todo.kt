package com.example.domain.model

import com.google.firebase.firestore.DocumentId

data class Todo(
    @DocumentId
    val id: Long,
    val title: String,
    val body: String,
    val author: Author,
    val createdAt: String,
    var isImportant: Boolean = false,
)