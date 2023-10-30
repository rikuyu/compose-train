package com.example.model

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
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

data class CharacterDetail(
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
    val backgroundColor: Color,
) {
    companion object {
        fun convertToDetail(
            character: Character?,
            isFavorite: Boolean,
            backgroundColor: Color,
        ): CharacterDetail? {
            val c = character ?: return null
            return CharacterDetail(
                id = c.id,
                created = c.created,
                episode = c.episode,
                gender = c.gender,
                image = c.image,
                name = c.name,
                species = c.species,
                status = c.status,
                type = c.type,
                url = c.url,
                isFavorite = isFavorite,
                backgroundColor = backgroundColor,
            )
        }

        fun convertToCharacter(character: CharacterDetail): Character = Character(
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
        )
    }
}
