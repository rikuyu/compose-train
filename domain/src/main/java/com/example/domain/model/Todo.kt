package com.example.domain.model

data class Todo(
    val id: Long,
    val title: String,
    val body: String,
    val author: Author,
    val createdAt: String,
    var isImportant: Boolean = false,
)