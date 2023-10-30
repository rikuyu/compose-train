package com.example.data.repository

import com.example.model.Character

interface RickMortyRepository {

    suspend fun getCharacters(): List<Character>

    suspend fun getCharacter(id: Int): Character

    suspend fun getFavoriteCharacters(): List<Character>

    suspend fun insertCharacter(character: Character)

    suspend fun deleteCharacter(character: Character)

    suspend fun getIsFavorite(id: Int): Boolean
}
