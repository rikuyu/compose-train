package com.example.composetrainapp.domain.model.response

data class Info(
    val count: Int,
    val pages: Int,
    val next: String,
    val prev: Any
)