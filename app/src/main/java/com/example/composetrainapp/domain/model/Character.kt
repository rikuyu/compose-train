package com.example.composetrainapp.domain.model

import androidx.compose.runtime.Stable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Stable
@Entity(tableName = "characters")
data class Character(
    @PrimaryKey val id: Int,
    val created: String,
    val episode: List<String>,
    val gender: String,
    val image: String,
    val name: String,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)

data class DetailCharacter(
    val id: Int,
    val created: String,
    val episode: List<String>,
    val gender: String,
    val image: String,
    val name: String,
    val species: String,
    val status: String,
    val type: String,
    val url: String,
    val isFavorite: Boolean
)