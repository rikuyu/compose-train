package com.example.data.data_source.local

import com.example.model.Character

interface LocalDataSource {

    suspend fun getFavoriteCharacters(): List<Character>

    suspend fun insertCharacter(character: Character)

    suspend fun deleteCharacter(character: Character)

    suspend fun getIsFavorite(id: Int): Boolean
}