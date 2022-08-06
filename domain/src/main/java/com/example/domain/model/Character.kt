package com.example.domain.model

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
    val url: String,
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
    val isFavorite: Boolean,
) {
    companion object {
        fun convertToDetail(character: Character, isFavorite: Boolean): DetailCharacter = DetailCharacter(
            id = character.id,
            created = character.created,
            episode = character.episode,
            gender = character.gender,
            image = character.image,
            name = character.name,
            species = character.species,
            status = character.status,
            type = character.type,
            url = character.url,
            isFavorite = isFavorite
        )

        fun convertToCharacter(character: DetailCharacter): Character = Character(
            id = character.id,
            created = character.created,
            episode = character.episode,
            gender = character.gender,
            image = character.image,
            name = character.name,
            species = character.species,
            status = character.status,
            type = character.type,
            url = character.url
        )
    }
}